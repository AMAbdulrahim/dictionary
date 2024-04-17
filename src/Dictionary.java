
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    private AVLTree<String> tree;

    public Dictionary() {
        tree = new AVLTree<String>();
    }

    public Dictionary(String s) throws WordAlreadyExistsException {
        this(); // the tree initilaization
        addWord(s);
       
    }

    public Dictionary(File f) {
        this(); // the tree initilaization
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNext()) // while the file has lines
                tree.insert(sc.next());// add them to the tree
                
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWord(String s) throws WordAlreadyExistsException{
        if(tree.search(s))    // if the woord is found so no need to add it 
            throw new WordAlreadyExistsException(); // rise an error
        else
            tree.insert(s); // if it is not in the tree
    }

    public boolean findWord(String s) {
        return tree.search(s); // using the same BST search
    }

    public void deleteWord(String s)  throws WordNotFoundException{
        if(tree.search(s))     // same to delete by any method
            tree.deleteByCopying(s);  
            // tree.deleteByMerging(s);
        else
            throw new WordNotFoundException();
    }

    public String[] findSimilar(String s) {
        List<String> simWrds = new ArrayList<>();
        int len = s.length();
    
        // looping each char with each alphabet letter and checking
        for (int i = 0; i < len; i++) 
            for (char c = 'a'; c <= 'z'; c++) 
                if (c != s.charAt(i)) {
                    String word = s.substring(0,i) + c + s.substring(i+1);
                    if (findWord(word)) 
                        simWrds.add(word);
                }
        // inserting each alphabet letter at each position and checking
        for (int i = 0; i <= len; i++) 
            for (char c = 'a'; c <= 'z'; c++) {
                String word = s.substring(0,i) + c + s.substring(i);
                if (findWord(word)) // if so 
                    simWrds.add(word); // add it
            }
        // deleting each char and checking
        for (int i = 0; i < len; i++) {
            String word = s.substring(0,i) + s.substring(i+1);
            if (findWord(word)) // if so 
                simWrds.add(word); // add it
        }
        // swapping adjacent chars and checking
        for (int i = 0; i < len - 1; i++) {
            String word = s.substring(0,i) + s.charAt(i+1) + s.charAt(i) + s.substring(i+2);
            if (findWord(word)) // if so 
                simWrds.add(word); // add it
        }
    
        return simWrds.toArray(new String[simWrds.size()]);
    }
    
    
    public void saveToFile(File f) {
        try (BufferedWriter rtr = new BufferedWriter(new FileWriter(f))) {
            BTNode<String> curr = tree.root; // start from the root node of the tree
            BTNode<String> prev;
    
            while (curr != null) {
                if (curr.left == null) { // if the current node has no left child
                    rtr.write(curr.data.toString()); // write the current node's data to the file
                    rtr.newLine();
                    curr = curr.right;// move to the right child
                } 


                else { // if the current node has a left child 
                    prev = curr.left; // traverse its left subtree first
    
                    // find the right node in the left of the current node
                    while (prev.right != null && prev.right != curr) 
                        prev = prev.right;
    
                    if (prev.right == null) { // if the right child of the right node is null,
                        prev.right = curr; // set it to the current node 
                        curr = curr.left; // then go to the left 
                    } else { // if the right child of the right node is not null, so it already traversed the left, and move to the right child
                        prev.right = null;
                        rtr.write(curr.data.toString()); // so write the current node's data to the file
                        rtr.newLine();
                        curr = curr.right; // then go to the right child
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("error saving the file: " + e.getMessage());
        }
    }
    
    
    

}