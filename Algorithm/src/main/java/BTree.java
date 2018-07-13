public class BTree {

public Node current;
public Node temp;


public Node addItem(Node head,int item){
if(head == null){
    head = new Node(item);
    return  head;
}
  if(item>head.item){
        head.right = addItem(head.right,item);
    }
    if(item<head.item){
        head.left = addItem(head.left,item);
    }
    else{
        return head;
    }

 return head;
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


    }
}
