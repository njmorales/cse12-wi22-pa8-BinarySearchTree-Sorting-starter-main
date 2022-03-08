/**
 * Name: Nathan Morales
 * ID: A17073905
 * Email: njmorales@ucsd.edu
 * Sources used: zybooks
 * 
 * This file contains the class MyBSTIterartor, which itself contains
 * the nested classes MyBSTNodeIterator, MyBSTKeyIterator, 
 * and MyBSTValueIterator. 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class allows an iterator to be created over a MyBST structure.
 * THe iterator starts at the root and is able to in-order traverse to the
 * end of the tree. The iterator stores the last visited node's value
 * as well as the next node's value and has the ability to remove the last
 * visited node from the tree. 
 */
public class MyBSTIterator<K extends Comparable<K>, V> extends MyBST<K, V> {
    abstract class MyBSTNodeIterator<T> implements Iterator<T> {
        MyBSTNode<K, V> next;
        MyBSTNode<K, V> lastVisited;

        /**
         * Constructor that initializes the node iterator
         *
         * @param first The initial node that next points
         */
        MyBSTNodeIterator(MyBSTNode<K, V> first) {
            next = first;
            lastVisited = null;
        }

        /**
         * This method is used for determining if the next pointer in the
         * iterator points to null.
         *
         * @return If next is null based on the current position of iterator
         */
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Advances the iterator to the next node and returns the node 
         * we have now visited. 
         * @return the next node the iterator advanced to
         */
        MyBSTNode<K, V> nextNode() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastVisited = this.next; //update lastVisited
            this.next = this.next.successor();//advance next to it's successor
            return this.lastVisited; //return most recent node visited
        }

        /**
         * This method removes the last visited node from the tree.
         */
        public void remove() {
            if (lastVisited == null) { //the iterator hasn't visited any nodes
                throw new IllegalStateException(); //throw exception
            }
            //if lastVisited has both children
            if (lastVisited.getRight() != null &&
                    lastVisited.getLeft() != null) {
                //retreat iterator in anticipation of removing a node
                next = lastVisited; 
            }
            //remove the last visited node
            MyBSTIterator.this.remove(lastVisited.getKey()); 
            //set lastVisited to null because it was just removed
            lastVisited = null;
        }
    }

    /**
     * BST Key iterator class that extends the node iterator.
     */
    class MyBSTKeyIterator extends MyBSTNodeIterator<K> {

        MyBSTKeyIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node key
         *
         * @return K the next key
         */
        public K next() {
            return super.nextNode().getKey();
        }
    }

    /**
     * BST value iterator class that extends the node iterator.
     */
    class MyBSTValueIterator extends MyBSTNodeIterator<V> {

        /**
         * Call the constructor method from node iterator
         *
         * @param first The initial value that next points
         */
        MyBSTValueIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node value
         *
         * @return V the next value
         */
        public V next() {
            return super.nextNode().getValue();
        }
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTKeyIterator getKeyIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTKeyIterator(curr);
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTValueIterator getValueIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTValueIterator(curr);
    }
}