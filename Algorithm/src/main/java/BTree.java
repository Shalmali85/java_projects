public class BTree {

public Node current;
public Node temp;


public Node addItem(Node current,int item){
if(current == null){
    current = new Node(item);
    return  current;
}
  if(item>current.item){
      current.right = addItem(current.right,item);
    }
    if(item<current.item){
        current.left = addItem(current.left,item);
    }
    else{
        return current;
    }

 return current;
}


public void addItemNonRecursive(int item){
    temp = current;
    if(current == null){
       current = new Node(item);
    }
    else{
        while(true){
            if(item> current.item){
                if(current.right == null) {
                    current.right = new Node(item);
                    break;
                }
                current = current.right;

            }
            if(item< current.item){
                if(current.left == null) {
                current.left = new Node(item);
                break;
            }
            current = current.left;

        }
        }

        current = temp;
    }
}
public void add(int item){
    //current = addItem(current,item);
    addItemNonRecursive(item);
}





    public class Node {

    private int item;
    private Node left;
    private Node right;


    public Node(int item){
        this.item=item;
    }


    }
    public void inOrderTraversal(){
        inOrderTraversal(current);
    }
    public void inOrderTraversal(Node node){
    if(node.left!=null)
        inOrderTraversal(node.left);
        System.out.println(node.item);
        if(node.right!=null)
        inOrderTraversal(node.right);
    }
    public boolean searchTree(int item) {
      boolean found =  searchTree(current,item);
      System.out.println(found);
      return found;
    }

    public boolean searchTree(Node node, int item) {
    if(node != null && item == node.item){
        return true;
    }
    if (node != null && item > node.item) {
          return searchTree(node.right,item);
    }
    if(node != null && item < node.item){
             return searchTree(node.left,item);
        }
    return false;
    }

    public static void main(String args[]){
    BTree tree = new BTree();
    tree.add(5);
    tree.add(10);
    tree.add(15);
    tree.add(14);
    tree.add(20);
    tree.add(-10);
    tree.add(-8);
    tree.add(0);
    tree.inOrderTraversal();
    tree.searchTree(-8);
    }
}
