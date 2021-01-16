package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import driver.*;

public class TreeTest {
	private Tree tree;
	private FileSystemShell sys;
	private ErrorHandler err;
	
	@Before
	public void setUp()
	{	
		this.sys = FileSystem.createFileSystemInstance();
		this.err = new ErrorHandler(sys);
		this.tree = new Tree(sys, err);
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
	public void testTree() {
		String[] args = {"tree"};
		sys.createDirectory("test1");
		tree.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "/\n" + 
				"\ttest1\n\n");
	}
	
	@Test
	public void testTreeParam() {
		String[] args = {"tree", "param"};
		sys.createDirectory("test1");
		tree.run(args, "");
		assertTrue(ErrorHandler.checkIfErrorOccurred());
	}
	
	@Test
	public void testSubDirs() {
		String[] args = {"tree"};
		sys.createDirectory("test1");
		sys.createDirectory("test1/test2");
		sys.createDirectory("test1/test3");
		sys.createFile("test1/test3/file1", "");
		sys.createFile("test1/test3/file2", "");
		tree.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "/\n" + 
				"\ttest1\n" + 
				"\t\ttest3\n" + 
				"\t\t\tfile2\n" + 
				"\t\t\tfile1\n" + 
				"\t\ttest2\n\n");
	}
	@Test
	public void testNotAtRoot() {
		String[] args = {"tree"};
		sys.createDirectory("test1");
		sys.createDirectory("test1/test2");
		sys.createDirectory("test1/test3");
		sys.createFile("test1/test3/file1", "");
		sys.createFile("test1/test3/file2", "");
		sys.setCurrentNode(sys.getDirectory("test1"));
		tree.run(args, "");
		assertFalse(ErrorHandler.checkIfErrorOccurred());
		assertEquals(Output.getLastPrinted(), "/\n" + 
				"\ttest1\n" + 
				"\t\ttest3\n" + 
				"\t\t\tfile2\n" + 
				"\t\t\tfile1\n" + 
				"\t\ttest2\n\n");
	}
	

    @Test
    public void testRedirection() {
        String[] args = {"tree"};
        sys.createDirectory("test1");
        tree.run(args, "> outFile");
        assertFalse(ErrorHandler.checkIfErrorOccurred());
        assertEquals(sys.getCurrentNode().getFile("outFile").getData(), "/\n" + 
                "\ttest1\n");
    }

}
