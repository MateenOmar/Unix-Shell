package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.ErrorHandler;
import driver.FileSystem;
import driver.FileSystemShell;
import driver.MakeDirectory;

public class MakeDirectoryTest {

	private MakeDirectory mkdir;
	private FileSystemShell sys;
	private ErrorHandler err;
	
	@Before
	public void setUp()
	{	
		this.sys = FileSystem.createFileSystemInstance();
		this.err = new ErrorHandler(sys);
		this.mkdir = new MakeDirectory(sys, err);
		ErrorHandler.resetErrorOccurred();
		System.out.println("===============TEST==============");
	}
	@After
	public void tearDown() throws Exception
	{
	    Field field = (sys.getClass()).getDeclaredField("system");
	    field.setAccessible(true);
	    field.set(null, null);
	}
	@Test
	public void testMkdir() {
		String[] args = {"mkdir", "test1"};
		mkdir.run(args);
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		Directory test = sys.getDirectory("test1");
		assertNotNull(test);
	}
	
	@Test
	public void testInvalidName() {
		String[] args = {"mkdir", "test1$#@$#(&@!#"};
		mkdir.run(args);
		assertTrue(ErrorHandler.checkIfErrorOccurred());
		ErrorHandler.resetErrorOccurred();
		Directory test = sys.getDirectory("test1$#@$#(&@!#");
		assertEquals(test,null);
	}
	
	@Test
	public void testMultipleDirs() {
		String[] args = {"mkdir", "test1", "test1/test2"};
		mkdir.run(args);
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		Directory test = sys.getDirectory("test1");
		assertNotNull(test);
		test = sys.getDirectory("test1/test2");
		assertNotNull(test);
	}
	@Test
	public void testNoParams() {
		String[] args = {"mkdir"};
		mkdir.run(args);
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}

}
