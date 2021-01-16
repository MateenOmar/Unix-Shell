package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.*;

import driver.*;

public class ListTest {
	
	private List ls;
	private FileSystemShell sys;
	private ErrorHandler err;
	
	@Before
	public void setUp()
	{	
		this.sys = FileSystem.createFileSystemInstance();
		this.err = new ErrorHandler(sys);
		this.ls = new List(sys, err);
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
	public void testNoParam() {
		sys.createDirectory("temp1");
		String[] args = {"ls"};
		ls.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "temp1\n\n");
	}
	
	@Test
	public void testPaths() {
		sys.createDirectory("temp1");
		sys.createDirectory("temp1/temp2");
		sys.createDirectory("temp1/temp3");
		String[] args = {"ls", "temp1"};
		ls.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "temp1:\n" + 
				"temp2\n" + 
				"temp3\n" + 
				"\n" + 
				"\n");
	}
	@Test
	public void testRecursive() {
		sys.createDirectory("temp1");
		sys.createDirectory("temp1/temp2");
		sys.createDirectory("temp1/temp3");
		String[] args = {"ls","-R", "temp1"};
		ls.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"temp1:\n" + 
				"temp2\n" + 
				"temp3\n" + 
				"\n" + 
				"temp3:\n" + 
				"\n" + 
				"temp2:\n" + 
				"\n" + 
				"\n");
	}
	
	@Test
	public void testNoPath()
	{
		sys.createDirectory("temp1");
		sys.createDirectory("temp1/temp2");
		String[] args = {"ls", "temp2"};
		ls.run(args, "");
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testMultiplePaths()
	{
		sys.createDirectory("temp1");
		sys.createDirectory("temp1/temp2");
		String[] args = {"ls", "temp1", "temp1/temp2"};
		ls.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testRedirection()
	{
	  sys.createDirectory("temp1");
	  sys.createDirectory("temp2");
	  sys.createDirectory("temp3");
      String[] args = {"ls"};
      ls.run(args, "> outFile");
      assertEquals(sys.getCurrentNode().getFile("outFile").getData(),"temp1\n"+
      "temp2\ntemp3\n");
	}

}
