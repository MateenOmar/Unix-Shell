package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.*;
import driver.*;

public class PrintWorkingDirectoryTest {

	private PrintWorkingDirectory pwd;
	private FileSystemShell sys;
	private ErrorHandler err;
	
	@Before
	public void setUp()
	{	
		this.sys = FileSystem.createFileSystemInstance();
		this.err = new ErrorHandler(sys);
		this.pwd = new PrintWorkingDirectory(sys, err);
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
	public void testPwd() {
		String[] args = {"pwd"};
		sys.createDirectory("test1");
		pwd.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "/\n");
	}
	@Test
	public void testWithParam() {
		String[] args = {"pwd","param"};
		sys.createDirectory("test1");
		pwd.run(args, "");
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testNotRoot() {
		String[] args = {"pwd"};
		sys.createDirectory("test1");
		Directory test = sys.getDirectory("test1");
		sys.setCurrentNode(test);
		pwd.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"/test1/\n");
	}

}
