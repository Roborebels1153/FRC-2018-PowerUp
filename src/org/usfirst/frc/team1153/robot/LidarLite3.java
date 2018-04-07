package org.usfirst.frc.team1153.robot;


/*----------------------------------------------------------------------------*/
/* LIDARLite RoboRio Java driver derived from Garmin's Arduino C++ driver.
/*----------------------------------------------------------------------------*/


import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.hal.I2CJNI;


/**
 * This is a bare-bones RoboRio Java class supporting the "LIDAR-Lite v3"
 * optical distant measurement sensor from GARMIN sensor products,
 * part Number 010-01722-00.  This is NOT the "High Performance" (HP)
 * version of the GARMIN LIDAR product line.
 *
 * The product page is located at https://buy.garmin.com/en-US/US/p/557294
 * and contains links to manuals, reference software and their Support Center.
 * Many other sites contain similar info by a simple search for "LidarLite3".
 *
 * This Java class is derived from the GARMIN C++ Arduino reference code.
 * It uses the I2C LIDAR interface.  Note that the LIDAR hardware also supports
 * a PWM interface, but PWM mode is not implemented here.
 */
public class LidarLite3 {

	public enum Configuration {
		// LL3 driver configurations (mode) settings...
		// Default mode, balanced performance.
		LL3_CFG_DEFAULT,

		// Short range, high speed. Uses 0x1d maximum acquisition count.
		LL3_CFG_SHORT_AND_FAST,

		// Default range, higher speed.  Turns on quick termination detection
		// for faster measurements at short range (with decreased accuracy).
		LL3_CFG_DEFAULT_AND_FASTER,

		// Maximum range. Forces maximum acquisition count.
		LL3_CFG_MAX_AND_SLOWEST,

		// High sensitivity detection. Overrides default valid measurement detection
		// algorithm, and uses a threshold value for high sensitivity with increased noise.
		LL3_CFG_HIGH_SENSITIVITY,

		// Low sensitivity detection. Overrides default valid measurement detection
		// algorithm, and uses a threshold value for low sensitivity with reduced noise.
		LL3_CFG_LOW_SENSITIVITY,
	}

	// Autoincrement mode address bit (must be set explicitly)
	public static final int LL3_AUTOINCREMENT_ADDRESSING = 0x80;

	// busy bit in status register
	public static final int LL3_STATUS_BUSY_BIT = 0x01;

	// Key Register Addresses
	public static final int LL3_REG_COMMAND       = 0x00; // command register
	public static final int LL3_REG_STATUS        = 0x01; // status register
	public static final int LL3_REG_ACQ_COUNT     = 0x02; // max acquisition count
	public static final int LL3_REG_ACQ_MODE      = 0x04; // acquisition mode control
	public static final int LL3_REG_DISTANCE      = 0x0f; // distance measure high byte
	public static final int LL3_REG_THRESHOLD     = 0x1c; // peak detect threshold bypass
	public static final int LL3_REG_TEST_MODE     = 0x40; // test mode control register
	public static final int LL3_REG_CORR_DATA     = 0x52; // Correlation data high byte
	public static final int LL3_REG_CORR_BANK     = 0x5d; // Correlation record memory bank select
	public static final int LL3_MAX_REG_ADDRESS   = 0x6d; // highest allowed register address

	// Device commands
	public static final int LL3_DEV_CMD_DEVICE_RESET               = 0x00;
	public static final int LL3_DEV_CMD_MEASURE_WITHOUT_CORRECTION = 0x03;
	public static final int LL3_DEV_CMD_MEASURE_WITH_CORRECTION    = 0x04;

	// Test mode command parameters
	public static final int LL3_TEST_MODE_DISABLE = 0x00;
	public static final int LL3_TEST_MODE_ENABLE  = 0x07;
	
	// begin flag
	private static boolean m_begin;

	// device address
	private static final byte k_deviceAddress = 0x62;

	// port
	private final byte m_port = (byte)I2C.Port.kOnboard.value;

	// buffer
	private final ByteBuffer m_buffer = ByteBuffer.allocateDirect(2);
	
	//offset
	private static int  m_offset;
	
	// scale factor
	private static double m_scale;



	/*------------------------------------------------------------------------------

	  Constructor

	  LidarLite3 inherits from I2C base class, specifying I2C Port and device address.

	------------------------------------------------------------------------------*/
	public LidarLite3() {
		// set port ID
		I2CJNI.i2CInitialize(m_port);
		
		// initialize begin flag
		m_begin = false;
	}


	/*----------------------------------------------------------------------------

	  Private device wait function

	  Wait until the busy status clears....

	------------------------------------------------------------------------------*/
	private void busywait() {
		byte[] status = new byte[1];

		status[0] = LL3_STATUS_BUSY_BIT;

		// wait for busy bit to clear
		while (LL3_STATUS_BUSY_BIT == (status[0] & LL3_STATUS_BUSY_BIT)) {
			read(LL3_REG_STATUS, 1, status);
		}
	}


	/*------------------------------------------------------------------------------

		Begin

		Starts the sensor and I2C.

		Parameters

		------------------------------------------------------------------------------

		configuration: Selects one of several preset configurations.

		fasti2c: Default 100 kHz. I2C base frequency.
			If true I2C frequency is set to 400kHz.
			NOTE: WPILib does not offer a "I2C.setClock()" API,
			so this param is ignored.

	------------------------------------------------------------------------------*/
	public void begin(Configuration config, boolean fasti2c, int offset, double scale) {
		
		// set flag
		m_begin = true;
		m_offset = offset;
		m_scale = scale;
		// issue hardware reset
		reset();

		// pass down user config
		configure(config);
		
		// Issue initial measurement command to prime data pipeline.
		// NOTE: In order to improve performance, calls to distance()
		// return previous measurement and then start a new measurement.
		// This 1st measurement command kick-starts the pipeline.
		write(LL3_REG_COMMAND, LL3_DEV_CMD_MEASURE_WITH_CORRECTION);
	}


	/*------------------------------------------------------------------------------

		Configure

		Selects one of several preset configurations.

		Parameters

		------------------------------------------------------------------------------

		configuration:  Default 0.

			0: Default mode, balanced performance.
			1: Short range, high speed. Uses 0x1d maximum acquisition count.
			2: Default range, higher speed short range. Turns on quick termination
			   detection for faster measurements at short range (with decreased
			   accuracy)
			3: Maximum range. Uses 0xff maximum acquisition count.
			4: High sensitivity detection. Overrides default valid measurement detection
			   algorithm, and uses a threshold value for high sensitivity and noise.
			5: Low sensitivity detection. Overrides default valid measurement detection
			   algorithm, and uses a threshold value for low sensitivity and noise.

			Use the "Configuration" enumerator to select.

	------------------------------------------------------------------------------*/
	public void configure(Configuration config) {

		// set the three control parameters to hardware reset values
		int count     = 0x80;
		int mode      = 0x08;
		int threshold = 0x00;
		
		if (false == m_begin) {
			System.out.println("Missing required call to LidarLite3::begin().");			
		}

		busywait();

		// change settings per user config value
		switch (config) {

			case LL3_CFG_DEFAULT:            // Default mode, balanced performance
				// nothing to do here...
				break;

			case LL3_CFG_SHORT_AND_FAST:     // Short range, high speed
				count = 0x1d;
				break;

			case LL3_CFG_DEFAULT_AND_FASTER: // Default range, higher speed
				mode = 0x00;
				break;

			case LL3_CFG_MAX_AND_SLOWEST:    // Maximum measurements
				count = 0xff;
				break;

			// NOTE: the two sensitivity settings are from the Arduino reference code,
			// which are different then those recommended in the LidarLite3 data sheet.

			case LL3_CFG_HIGH_SENSITIVITY:   // High sensitivity, higher error
				threshold = 0x80;
				break;

			case LL3_CFG_LOW_SENSITIVITY:    // Low sensitivity, lower error
				threshold = 0xb0;
				break;

			default:
				// undefined enum value
				System.out.println("bad config");

		}  // end switch

		// write the control registers
		write(LL3_REG_ACQ_COUNT, count);
		write(LL3_REG_ACQ_MODE,  mode);
		write(LL3_REG_THRESHOLD, threshold);
	}


	/*------------------------------------------------------------------------------

	  Reset

	  Reset device. The device reloads default register settings, including the

	  default I2C address. Re-initialization takes approximately 22ms.

	------------------------------------------------------------------------------*/
	public void reset() {
		write(LL3_REG_COMMAND, LL3_DEV_CMD_DEVICE_RESET);
	}


	/*------------------------------------------------------------------------------

	  Distance

	  Take a distance measurement (in centimeters) and read the result.
	  
	  This implementation does a "pipelined" read for faster response.
	  This reduces the measurement blocking time by moving the measurement
	  overhead outside of the distance call.  This allows the measurement
	  process to run while the rest of the system is doing other stuff.
	  
	  	1. do a busy-wait in case the last measurement is incomplete.
	  	2. do a data read... of the *previous* measurement.
	  	3. initiate the "next" measurement, in preparation for
	  		the following distance call.
	  	4. return result of previous measurement.

	  THE RESULT IS ALWAYS POSITIVE AND IN UNITS OF CENTIMETERS.

	  Parameters

	  ------------------------------------------------------------------------------

	  biasCorrection:
		TRUE: Take acquisition with receiver bias correction.
		FALSE: Faster measurements, with progressively less accuracy.

		Receiver BIAS CORRECTION SHOULD BE PERFORMED PERIODICALLY. (e.g. 1 out
		of every 100 readings).

	------------------------------------------------------------------------------*/
	public int distance(boolean biasCorrection) {

		int distance;
		byte[] byteArray = new byte[2];

		if (false == m_begin) {
			System.out.println("Missing required call to LidarLite3::begin().");			
		}

		// wait for busy bit to clear in case previous measurement is incomplete
		busywait();

		// Read two consecutive result bytes from distance registers (0x0f and 0x10)
		read(LL3_REG_DISTANCE, 2, byteArray);

		// Upshift the high byte and add low byte.
		// (distance is always positive, so no need to sign-extend)
		distance = (int)Integer.toUnsignedLong(byteArray[0] << 8) + Byte.toUnsignedInt(byteArray[1]);

		// launch a new measurement prior to exit
		if (biasCorrection) {
			write(LL3_REG_COMMAND, LL3_DEV_CMD_MEASURE_WITH_CORRECTION);
		} else {
			write(LL3_REG_COMMAND, LL3_DEV_CMD_MEASURE_WITHOUT_CORRECTION);
		}

		return (int) ((distance - (m_offset)) * m_scale);
	}


	/*------------------------------------------------------------------------------


	  write

	  Perform a single-byte I2C write to LIDARLite device.

	  Parameters

	  ------------------------------------------------------------------------------

	  regAddress: register address to write to.
	  writeData: value to write (one byte).

	------------------------------------------------------------------------------*/
	private void write(int regAddress, int writeData) {

		// range-check register address
		if (regAddress > LL3_MAX_REG_ADDRESS) {
			System.out.println("bad lidar write address");
		}

		// range-check register data
		if (regAddress > 255) {
			System.out.println("bad lidar write data");
		}

		// load the register address and write data
		m_buffer.put(0, (byte)regAddress);
		m_buffer.put(1, (byte)writeData);

		// write it
		I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte)2);
	}


	/*------------------------------------------------------------------------------

	  Read

	  Perform I2C read from device.

	  Parameters

	  ------------------------------------------------------------------------------

	  regAddress: register address to read from.
	  numBytes: numbers of bytes to read. Can be 1 or 2.
	  readData: an array to store the read values.

	------------------------------------------------------------------------------*/
	private void read(int regAddress, int numBytes, byte readData[]) {

		// Avoid inherent repeat-start sequence apparently used in WPILib I2C
		// base class "read" API; repeat-start (normally used to avoid bus
		// arbitration loss on a multi-master I2C bus) is not supported by this
		// device.
		//
		// UPDATE: The WPILib::I2C class is buggy and does not work on either port.
		// This new Implementation accesses the I2C hardware directly through the
		// HAL I2CJNI interface, bypassing the buggy WPILib::I2C class.
		//
		// Instead, write the read address separately, followed by explicit raw
		// byte reads.  Currently, only single-byte and two-byte reads are supported.

		// range-check register address
		if (regAddress > LL3_MAX_REG_ADDRESS) {
			System.out.println("bad lidar read address");
		}

		// range-check byte count (limited by m_buffer allocation)
		if (numBytes > 2) {
			System.out.println("bad lidar read byte count");
		}

		// set auto-increment addressing bit if reading more than one byte
		if (numBytes > 1)
			regAddress |= LL3_AUTOINCREMENT_ADDRESSING;

		// write the read address (*with* a StopCondition)
		m_buffer.put(0, (byte)regAddress);
		I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte)1);

		// read the data
		I2CJNI.i2CRead(m_port, k_deviceAddress, m_buffer, (byte)numBytes);

		// copy it out to user buffer
		for (int i = 0;  i < numBytes; i++) {
			readData[i] = m_buffer.get(i);
		}
	}


	/*------------------------------------------------------------------------------

	  Correlation Record To Serial

	  The correlation record used to calculate distance can be read from the device.
	  It has a bipolar wave shape, transitioning from a positive going portion to a
	  roughly symmetrical negative going pulse. The point where the signal crosses
	  zero represents the effective delay for the reference and return signals.

	  Process

	  ------------------------------------------------------------------------------

	  1.  Take a distance reading (there is no correlation record without at least
		  one distance reading being taken)
	  2.  Select memory bank by writing 0xc0 to register 0x5d
	  3.  Set test mode select by writing 0x07 to register 0x40
	  4.  For as many readings as you want to take (max is 1024)
		  1.  Read two bytes from 0xd2
		  2.  The Low byte is the value from the record
		  3.  The high byte is the sign from the record

	  Parameters

	  ------------------------------------------------------------------------------

	  separator: the separator between serial data words
	  numberOfReadings: Default: 256. Maximum of 1024

	------------------------------------------------------------------------------*/
	public void correlationRecordToSerial(char separator, int numberOfReadings) {

		// Array to store read values
		byte[] correlationArray = new byte[2];

		// Var to store value of correlation record
		int correlationValue = 0;

		if (false == m_begin) {
			System.out.println("Missing required call to LidarLite3::begin().");			
		}

		//  Selects memory bank
		write(LL3_REG_CORR_BANK, 0xc0);  /* !!! undocumented magic number !!! */

		// Test mode enable
		write(LL3_REG_TEST_MODE, LL3_TEST_MODE_ENABLE);

		for (int i = 0; i < numberOfReadings; i++) {

			// Select correlation bytes
			read(LL3_REG_CORR_DATA, 2, correlationArray);

			//  Low byte is the value of the correlation record
			correlationValue = correlationArray[0];

			// if upper byte lsb is set, the value is negative
			if ((int)correlationArray[1] == 1){
				correlationValue |= 0xff00;  /* sign-extend */
			}

			System.out.print(correlationValue);
			System.out.print(separator);
		}

		// test mode disable
		write(LL3_REG_TEST_MODE, LL3_TEST_MODE_DISABLE);
	}
}
