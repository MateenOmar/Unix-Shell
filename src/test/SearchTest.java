package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.*;
import driver.*;

public class SearchTest 
{
	
	private Search search;
	private FileSystemShell sys;
	private ErrorHandler err;
		
	@Before
	public void setUp()
	{	
		this.sys = FileSystem.createFileSystemInstance();
		this.err = new ErrorHandler(sys);
		this.search = new Search(sys, err);
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
	public void testSearchDir() {
		String[] args = {"search", "test1", "-type", "d", "-name", "test2"};
		sys.createDirectory("test1");
		sys.createDirectory("test1/test2");
		search.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"/test1/test2\n\n");
	}
	
	@Test
	public void testSearchFile() {
		String[] args = {"search", "test1", "-type", "f", "-name", "test2"};
		sys.createDirectory("test1");
		sys.createFile("test1/test2", "test");
		search.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"/test1/test2\n\n");
	}
	
	@Test
	public void testSearchWrongType() {
		String[] args = {"search", "test1", "-type", "d", "-name", "test2"};
		sys.createDirectory("test1");
		sys.createFile("test1/test2", "test");
		search.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"\"test2\" not found in /test1/\n\n");
	}
	
	@Test
	public void testImproperParams() {
		String[] args = {"search", "test1", "-NOTTYPE", "d", "-NOTNAME", "test2"};
		sys.createDirectory("test1");
		sys.createFile("test1/test2", "test");
		search.run(args, "");
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testImproperParams2() {
		String[] args = {"search", "test1", "-type", "g", "-name"};
		sys.createDirectory("test1");
		sys.createFile("test1/test2", "test");
		search.run(args, "");
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testMultiplePaths() {
		String[] args = {"search", "test1", "test3", "-type", "f", "-name", "test2"};
		sys.createDirectory("test1");
		sys.createFile("test1/test2", "test");
		sys.createDirectory("test3");
		sys.createFile("test3/test2", "test");
		search.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(),"/test1/test2\n" + 
		"/test3/test2\n\n");
	}

    @Test
    public void testRedirection() {
        String[] args = {"search", "test1", "-type", "d", "-name", "test2"};
        sys.createDirectory("test1");
        sys.createDirectory("test1/test2");
        search.run(args, "> outFile");
        assertFalse(ErrorHandler.checkIfErrorOccurred());
        assertEquals(sys.getCurrentNode().getFile("outFile").getData(),"/test1/test2\n");
    }

}
