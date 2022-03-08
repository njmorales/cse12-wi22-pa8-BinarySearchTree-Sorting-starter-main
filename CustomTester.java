/**
 * Name: Nathan Morales
 * ID: A17073905
 * Email: njmorales@ucsd.edu
 * Sources used: zybooks
 * 
 * This file contains the CustomTester class, which includes the tests
 * I wrote for MyBST, MyBSTIterator, and MyCalendar that are not specified
 * in the PublicTester. These tests include various edge cases for the 
 * methods of these classes.
 */

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * This class contains the custom tests I wrote for MyBST, MyBSTIterator, 
 * and MyCalendar. These tests cover various edge cases for the methods of 
 * these classes, which include cases where the tree is empty or illegal 
 * arguments are passed into the class' methods. 
 */
public class CustomTester {

    MyBST<Integer, Integer> tree;
    MyBST<Integer, Integer> emptyTree = new MyBST<>();
    
    @Before
    public void setup(){
        MyBST.MyBSTNode<Integer, Integer> root = 
            new MyBST.MyBSTNode(5, 5, null);
        MyBST.MyBSTNode<Integer, Integer> three = 
            new MyBST.MyBSTNode(3, 3, root);
        MyBST.MyBSTNode<Integer, Integer> seven = 
            new MyBST.MyBSTNode(7, 7, root);
        MyBST.MyBSTNode<Integer, Integer> two = 
            new MyBST.MyBSTNode(2, 2, three);
        MyBST.MyBSTNode<Integer, Integer> four = 
            new MyBST.MyBSTNode(4, 4, three);
        MyBST.MyBSTNode<Integer, Integer> eight = 
            new MyBST.MyBSTNode(8, 8, seven);

        this.tree = new MyBST();
        this.tree.root = root;
        root.setLeft(three);
        root.setRight(seven);
        three.setLeft(two);
        three.setRight(four);
        seven.setRight(eight);
        this.tree.size = 6;
    }

    /**
     * test insert on an empty tree
     */
    @Test
    public void testInsert1(){
        emptyTree.insert(6, 6);
        assertEquals((Integer)6, emptyTree.root.getKey());
        assertEquals(1, emptyTree.size());
    }

    /**
     * test insert with a collision
     */
    @Test
    public void testInsert2(){
        tree.insert(7, 7);
        assertEquals((Integer)7, tree.root.getRight().getKey());
        assertEquals(6, tree.size());
    }

    /**
     * test insert with a collision at the root
     */
    @Test
    public void testInsert3(){
        tree.insert(5, 5);
        assertEquals((Integer)5, tree.root.getKey());
        assertEquals(6, tree.size());
    }

    /**
     * test insert on a left pointer
     */
    @Test
    public void testInsert4(){
        tree.insert(6, 6);
        assertEquals((Integer)6, tree.root.getRight().getLeft().getKey());
        assertEquals(7, tree.size());
    }

    /**
     * test search on an empty tree
     */
    @Test
    public void testSearch1(){
        assertEquals(null, emptyTree.search(8));
    }

    /**
     * test search on all items in a tree
     */
    @Test
    public void testSearch2(){
        assertEquals((Integer)5, tree.search(5));
        assertEquals((Integer)7, tree.search(7));
        assertEquals((Integer)8, tree.search(8));
        assertEquals((Integer)3, tree.search(3));
        assertEquals((Integer)4, tree.search(4));
        assertEquals((Integer)2, tree.search(2));
    }

    /**
     * test remove on the root
     */
    @Test
    public void testRemove1(){
        assertEquals((Integer)5, tree.remove(5));
        assertNull(tree.root.getRight().getRight());
        assertEquals(5, tree.size());
    }

    /**
     * test remove on the node with both children
     */
    @Test
    public void testRemove2(){
        assertEquals((Integer)3, tree.remove(3));
        assertEquals((Integer)4, tree.root.getLeft().getKey());
        assertNull(tree.root.getLeft().getRight());
        assertEquals(5, tree.size());
    }

    /**
     * test remove on the node with one child (both left or right)
     */
    @Test
    public void testRemove3(){
        assertEquals((Integer)7, tree.remove(7));
        assertEquals((Integer)8, tree.root.getRight().getKey());
        assertNull(tree.root.getRight().getRight());
        assertEquals(5, tree.size());
        tree.insert(6, 6);
        assertEquals(6, tree.size());
        assertEquals((Integer)6, tree.remove(6));
        assertNull(tree.root.getRight().getLeft());
        assertEquals(5, tree.size());
    }

    /**
     * test remove on all nodes
     */
    @Test
    public void testRemove4(){
        //remove each node one at a time, ensuring its reference becomes null
        assertEquals((Integer)5, tree.remove(5));
        assertEquals(5, tree.size());
        assertNull(tree.root.getRight().getRight());
        assertEquals((Integer)3, tree.remove(3));
        assertEquals(4, tree.size());
        assertNull(tree.root.getLeft().getRight());
        assertEquals((Integer)2, tree.remove(2));
        assertEquals(3, tree.size());
        assertNull(tree.root.getLeft().getLeft());
        assertEquals((Integer)4, tree.remove(4));
        assertEquals(2, tree.size());
        assertNull(tree.root.getLeft());
        assertEquals((Integer)7, tree.remove(7));
        assertEquals(1, tree.size());
        assertNull(tree.root.getRight());
        assertEquals((Integer)8, tree.remove(8));
        assertNull(tree.root);
        assertEquals(0, tree.size());
    }

    /**
     * test remove on root until tree is empty
     */
    @Test
    public void testRemove5() {
        while (tree.root != null) {
            tree.remove(tree.root.getKey());
        }
        assertNull(tree.root);
        assertEquals(0, tree.size());
    }

    /**
     * test in order method after various manipulations
     */
    @Test
    public void testInorder1(){
        tree.remove(4);
        tree.insert(6, 6);
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> expected 
            = new ArrayList<>();
        expected.add(tree.root.getLeft().getLeft());
        expected.add(tree.root.getLeft());
        expected.add(tree.root);
        expected.add(tree.root.getRight().getLeft());
        expected.add(tree.root.getRight());
        expected.add(tree.root.getRight().getRight());
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> actual 
            = tree.inorder();
        for (int i=0; i<expected.size(); i++){
            assertSame(expected.get(i), actual.get(i));
        }
    }

    /**
     * test in order on an empty array
     */
    @Test
    public void testInorder2(){
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> actual 
            = emptyTree.inorder();
        assertEquals(0, actual.size());
    }

    /**
     * test nextNode method on the whole tree
     * test nextNode at the end of the tree
     */
    @Test
    public void testIterator1(){
        MyBSTIterator<Integer, Integer> iterator = new MyBSTIterator<>();
        iterator.root = tree.root;
        MyBSTIterator<Integer, Integer>.MyBSTValueIterator valIt1 = 
            iterator.new MyBSTValueIterator(iterator.root);
        MyBST.MyBSTNode<Integer, Integer> curr = tree.root;
        //ensure iterator nextNode method works
        while (valIt1.hasNext()) {
            assertSame(curr, valIt1.nextNode());
            curr = curr.successor();
        }
        //try nextNode on last node
        try {
            MyBST.MyBSTNode<Integer, Integer> node = valIt1.nextNode();
        }
        catch (NoSuchElementException e) {
            //exception is caught so the test passes
        }
    }

    /**
     * test nextNode method after remove
     */
    @Test
    public void testIterator2(){
        MyBSTIterator<Integer, Integer> iterator2 = new MyBSTIterator<>();
        iterator2.root = tree.root;
        MyBSTIterator<Integer, Integer>.MyBSTValueIterator valIt2 = 
            iterator2.new MyBSTValueIterator(iterator2.root);
        valIt2.nextNode();
        valIt2.remove();
        assertNull(valIt2.lastVisited);
        assertEquals(tree.root, valIt2.nextNode());
    }

    /**
     * test various booking cases in MyCalendar
     */
    @Test
    public void testCalender1(){
        MyCalendar c = new MyCalendar();
        assertNotNull(c.getCalendar());
        //fill calendar
        assertTrue(c.book(6, 7));
        assertTrue(c.book(5, 6));
        assertTrue(c.book(7, 8));
        assertTrue(c.book(4, 5));
        assertTrue(c.book(8, 9));
        assertTrue(c.book(3, 4));
        assertTrue(c.book(9, 10));
        assertTrue(c.book(2, 3));
        assertTrue(c.book(10, 11));
        assertTrue(c.book(1, 2));
        assertTrue(c.book(11, 12));
        //ensure no double bookings
        assertFalse(c.book(6, 7));
        assertFalse(c.book(5, 7));
        assertFalse(c.book(2, 4));
        assertFalse(c.book(0, 2));
        assertFalse(c.book(10, 13));
    }

    /**
     * test invalid argument cases in MyCalendar
     */
    @Test
    public void testCalender2(){
        MyCalendar c = new MyCalendar();
        assertNotNull(c.getCalendar());
        //negative input
        try {
            c.book(-1, 2);
        }
        catch (IllegalArgumentException e) {
            //exception is caught so the test passes
        }//end <= start
        try {
            c.book(7, 5);
        }
        catch (IllegalArgumentException e) {
            //exception is caught so the test passes
        }
    }
}
