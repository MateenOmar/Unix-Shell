package test;

import static org.junit.Assert.*;

import org.junit.*;
import java.lang.reflect.Field;
import driver.ErrorHandler;
import driver.FileSystem;

public class FileSystemTest {
	
	private FileSystem sys;
	@Before
	public void setUp()
	{
		this.sys = (FileSystem) FileSystem.createFileSystemInstance();
		ErrorHandler.resetErrorOccurred();
	}
	
	@After
	public void tearDown() throws Exception
	{
	    Field field = (sys.getClass()).getDeclaredField("system");
	    field.setAccessible(true);
	    field.set(null, null); //setting the ref parameter to null
	}
	@Test
	public void testGetDir() {	
		sys.createDirectory("/test");
		sys.createDirectory("/test2");
		sys.getDirectory("test");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testGetFile() {	
		sys.createFile("/test","TESTCONTENTS");
		sys.createFile("/test2","TEST2CONTENTS");
		driver.File file = sys.getFile("test");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(file.getData(),"TESTCONTENTS");
	}
	
	@Test
	public void testRemoveFile() {
		sys.createFile("/test","TESTCONTENTS");
		sys.createFile("/test2","TEST2CONTENTS");
		sys.removeFile("test");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		driver.File file = sys.getFile("test");
		assertEquals(file, null);
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testNoDirFound() {
		sys.createDirectory("/test1");
		sys.createDirectory("/test2");
		driver.Directory dir = sys.getDirectory("test3");
		assertEquals(dir, null);
	}
	
	@Test
	public void testRelativePath() {
		sys.createDirectory("/test1");
		sys.createDirectory("/test1/test2");
		sys.createDirectory("/test1/test3");
		driver.Directory dir = sys.getDirectory("test1/test2/../test3");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(dir.getName(), "test3");
	}
	
	@Test
	public void testAbsolutePath() {
		sys.createDirectory("/test1");
		sys.createDirectory("/test1/test2");
		sys.createDirectory("/test1/test3");
		driver.Directory dir = sys.getDirectory("/test1/test3");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(dir.getName(), "test3");
	}

}
