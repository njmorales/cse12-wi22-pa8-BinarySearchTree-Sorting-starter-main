/**
 * Name: Nathan Morales
 * ID: A17073905
 * Email: njmorales@ucsd.edu
 * Sources used: zybooks
 * 
 * This file contains the class MyBST and a nested class MyBSTNode. 
 * MyBST is an implementation of Java's binary search tree data structure. 
 * MyBSTNode is a class that allows nodes for the MyBST to be created 
 * and stored. 
 */

import java.util.ArrayList;


/**
 * This class allows elements to be stored in a binary search tree structure.
 * They are stored using the MyBSTNode class that contains a key, a value,
 * and pointers to the left, right, and parent nodes.
 * The left child's key is less than the parent's key, and the right child's 
 * key is greater than the parent's key. 
 */
public class MyBST<K extends Comparable<K>,V>{
    MyBSTNode<K,V> root = null;
    int size = 0;

    /**
     * @return the number of nodes in the tree
     */
    public int size(){
        return this.size;
    }

    /**
     * Insert a new node containing the arguments key and value into the 
     * binary search tree according to the binary search tree properties. 
     * Update the tree size accordingly.
     * @param key key of node to be inserted
     * @param value  value of node to be inserted
     * @return value replaced by the new value (null if no replacement)
     */
    public V insert(K key, V value){
        if (key == null) {
            throw new NullPointerException();
        }
        MyBSTNode<K, V> toInsert = new MyBSTNode<K, V>(key, value, null);
        MyBSTNode<K, V> curr = this.root;
        V toReturn = null;
        if (curr == null) { //if the tree is empty, update the root
            this.root = toInsert;
            this.size++;
        }
        while (curr != null) { //traverse the tree
            //if insert key < current key, traverse left subtree
            if (toInsert.getKey().compareTo(curr.getKey()) < 0) {
                if (curr.getLeft() == null) { //if leaf node
                    curr.setLeft(toInsert); //set new node to left node
                    toInsert.setParent(curr); //set new node's parent
                    curr = null; //end while loop
                    this.size++;
                }
                else {
                    curr = curr.getLeft(); //traverse to left sub tree
                }
            }
            //if insert key > current key, traverse right subtree
            else if (toInsert.getKey().compareTo(curr.getKey()) > 0) {
                if (curr.getRight() == null) { //if leaf node
                    curr.setRight(toInsert); //set new node to right node
                    toInsert.setParent(curr); //set new node's parent
                    curr = null; //end while loop
                    this.size++;
                }
                else {
                    curr = curr.getRight();
                }
            }
            //if insert key = current key, replace value
            else {
                toReturn = curr.getValue(); //store old value
                curr.setValue(toInsert.getValue()); //replace old value
                return toReturn;
            }
        }
        return toReturn;
    }

    /**
     * Search for a node with key equal to key and return the value associated
     * with that node. The tree structure should not be affected by this.
     * @param key key of node to search for
     * @return the value of the node with this key (null if not found)
     */
    public V search(K key){
        if (key == null) { //keys in the tree can't be null
            return null;
        }
        MyBSTNode<K, V> curr = this.root;
        while (curr != null) { //traverse the tree starting at the root
            if (key.compareTo(curr.getKey()) == 0) { //keys match
                return curr.getValue(); //key found, return value
            }
            //search key is less than node's key
            else if (key.compareTo(curr.getKey()) < 0) { 
                curr = curr.getLeft(); //traverse left subtree
            }
            //search key is greater than node's key
            else {
                curr = curr.getRight(); //traverse right subtree
            }
        }
        return null; //key not found
    }

    /**
     * Search for a node with key equal to key and return the value associated
     * with that node. The node should be removed (disconnected) from the tree
     * and all references should be fixed, if needed. Update the tree size 
     * accordingly.
     * @param key key of node to remove
     * @return the value of the node being removed (null if not found)
     */
    public V remove(K key){
        if (key == null) { //keys in the tree can't be null
            return null;
        }
        MyBSTNode<K, V> curr = this.root;
        MyBSTNode<K,V> parent = null;
        while (curr != null) { //traverse the tree starting at the root
            if (curr.getKey() == key) { //keys match
                V currVal = curr.getValue(); //store value to return
                //remove leaf node
                if (curr.getLeft() == null && curr.getRight() == null) { 
                    if (parent == null) {
                        this.root = null;
                    }
                    else if (parent.getLeft() == curr) {
                        parent.setLeft(null);
                    }
                    else {
                        parent.setRight(null);
                    }
                    this.size--; //decrement size
                }
                //remove node with only left child
                else if (curr.getRight() == null) {
                    if (parent == null) {
                        this.root = curr.getLeft();
                    }
                    else if (parent.getLeft() == curr) {
                        parent.setLeft(curr.getLeft());
                    }
                    else {
                        parent.setRight(curr.getLeft());
                    }
                    this.size--; //decrement size
                }
                //remove node with only right child
                else if (curr.getLeft() == null) {
                    if (parent == null) {
                        this.root = curr.getRight();
                    }
                    else if (parent.getLeft() == curr) {
                        parent.setLeft(curr.getRight());
                    }
                    else {
                        parent.setRight(curr.getRight());
                    }
                    this.size--; //decrement size
                }
                //remove node with both children
                else {
                    MyBSTNode<K, V> successor = curr.successor();
                    K sucKey = successor.getKey(); //store successor's data
                    V sucVal = successor.getValue();
                    this.remove(sucKey); //recursively remove the successor
                    curr.setValue(sucVal); //replace curr with successor data
                    curr.setKey(sucKey);
                    if (curr == this.root) {
                        curr.setParent(null);
                    }
                }
                return currVal; //return value of removed node
            }
            //search key is less than node's key
            else if (key.compareTo(curr.getKey()) < 0) { 
                parent = curr;
                curr = curr.getLeft(); //traverse left subtree
            }
            //search key is greater than node's key
            else {
                parent = curr;
                curr = curr.getRight(); //traverse right subtree
            }
        }
        return null; //key not found
    }
    
    /**
     * Do an in-order traversal of the tree, adding each node to the end 
     * of an ArrayList, which will be returned.
     * @return an ArrayList of the ordered nodes
     */
    public ArrayList<MyBSTNode<K, V>> inorder(){
        ArrayList<MyBSTNode<K, V>> list = new ArrayList<>();
        MyBSTNode<K, V> curr = this.root;
        if (this.size() == 0) {
            return list;
        }
        while (curr.getLeft() != null) { //traverse to left most node
            curr = curr.getLeft(); //set curr to left most node
        }
        while (curr != null) { //progressively add successor nodes
            list.add(curr); //add current node to the list
            curr = curr.successor(); //update current to it's successor
        }
        return list;
    }

    static class MyBSTNode<K,V>{
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K,V> parent;
        private MyBSTNode<K,V> left = null;
        private MyBSTNode<K,V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         * @param key the key the MyBSTNode<K,V> will
         * @param value the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent){
            this.key = key;
            this.value = value;
            this.parent = parent; 
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey(){
            return key;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue(){
            return value;
        }

        /**
         * Return the parent
         * @return the parent
         */
        public MyBSTNode<K,V> getParent(){
            return parent;
        }

        /**
         * Return the left child 
         * @return left child
         */
        public MyBSTNode<K,V> getLeft(){
            return left;
        }

        /**
         * Return the right child 
         * @return right child
         */
        public MyBSTNode<K,V> getRight(){
            return right;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         * @param newKey the key to be stored
         */
        public void setKey(K newKey){
            this.key = newKey;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         * @param newValue the data to be stored
         */
        public void setValue(V newValue){
            this.value = newValue;
        }

        /**
         * Set the parent
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K,V> newParent){
            this.parent = newParent;
        }

        /**
         * Set the left child
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K,V> newLeft){
            this.left = newLeft;
        }

        /**
         * Set the right child
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K,V> newRight){
            this.right = newRight;
        }

        /**
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor(){
            if(this.getRight() != null){ //if there is a right node
                MyBSTNode<K,V> curr = this.getRight();//traverse right subtree
                while(curr.getLeft() != null){ //traverse to left most node
                    curr = curr.getLeft(); //set curr to the left most node
                }
                return curr; //return the successor
            }
            else{
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                /**
                 * if curr is parent's left node, return parent
                 * if curr is parent's right node, find the first left edge
                 * moving up the tree and return it's parent 
                 * (null if no left edges)
                 */
                while(parent != null && curr == parent.getRight()){
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /**
         * This method returns the in order predecessor of current node 
         * object. 
         * @return the predecessor of current node object
         */
        public MyBSTNode<K, V> predecessor(){
            if(this.getLeft() != null){ //if there is a left node
                MyBSTNode<K,V> curr = this.getLeft(); //traverse left subtree
                while(curr.getRight() != null){ //traverse to right most node
                    curr = curr.getRight(); //set curr to the right most node
                }
                return curr; //return the predecessor
            }
            else{
                MyBSTNode<K,V> parent = this.getParent();
                MyBSTNode<K,V> curr = this;
                /**
                 * if curr is parent's right node, return parent
                 * if curr is parent's left node, find the first right edge
                 * moving up the tree and return it's parent 
                 * (null if no right edges)
                 */
                while(parent != null && curr == parent.getLeft()){
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /** This method compares if two node objects are equal.
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj){
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K,V> comp = (MyBSTNode<K,V>)obj;
            
            return( (this.getKey() == null ? comp.getKey() == null : 
                this.getKey().equals(comp.getKey())) 
                && (this.getValue() == null ? comp.getValue() == null : 
                this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         * @return "Key:Value" that represents the node object
         */
        public String toString(){
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }
}