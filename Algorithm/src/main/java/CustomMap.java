
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomMap<K,V>  {

  private  Node <K,V>[] table ;

  public CustomMap(){
      table = new Node[200];
  }




    public void put(K key, V value){
      Node <K,V>[] entry;
      entry = table;
      if(key == null){
          return ;
      }
      int hash =this.generateHashCode(key);
      if(entry[hash] == null) {
          entry[hash] = new Node(key, value, null);
      }
      else {
          if(key.equals(entry[hash].key)){
              entry[hash] = new Node(key, value, null);
          }
          else{
              Node<K,V> current = entry[hash];
              while(true) {
                  if(current.next == null) {
                      current.next = new Node(key, value, null);
                      break;
                  }
                  else{
                      current = current.next;
                  }
              }
          }
      }
  }

   private  int generateHashCode(Object key){
      if(key instanceof String){
          return ((String) key).length()+((String) key).charAt(0);
      }
       return (Integer)key;
   }

    static  class Node<K,V> {
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value , Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public V setValue(V value) {
            return this.value = value;
        }
    }
    public V  get(K key){
      Node<K,V>[] entry;
      entry = table;
      if(key == null)
          return null;
      int hash = this.generateHashCode(key);
      if(entry[hash]!=null){
          if(entry[hash].next == null && key.equals(entry[hash].key)) {
              return entry[hash].value;
          }
          if(entry[hash].next != null){
              Node<K,V> current = entry[hash];
              while(current != null){
                  if(current.key == key) {
                      return current.value;
                  }
                  else
                  {
                      current= current.next;
                  }

            }
          }
      }
      return null;
    }

    public void remove(K key) {
      Node<K,V>[] entry = table;
        int hash = this.generateHashCode(key);
        if (entry[hash] != null && entry[hash].next == null && key.equals(entry[hash].key)) {
            entry[hash] = null;
        }
        if (entry[hash] != null && entry[hash].next != null) {
            Node<K, V> current = entry[hash];
           Node<K,V> prev=null;
                while (current != null) {
                    if (current.key.equals(key) && current.next == null) {
                        prev.next = null;
                        break;
                    }
                    if (current.key.equals(key) && current.next != null) {
                        if(prev != null) {
                            prev.next = current.next;
                        }
                        else{
                            entry[hash]=current.next;
                        }
                        break;
                    }
                    prev= current;
                    current = current.next;
                }
        }
    }

    public static void main(String ...args){
      CustomMap map = new CustomMap();
        map.put("apple",2);
        map.put("peach",12);
        map.put("orange",3);
        map.put("mango",4);
        map.put("pears",40);
        map.put("pineapple",5);
        System.out.println("Get value of orange ="+ map.get("orange"));
        System.out.println("Get value of mango ="+ map.get("mango"));
        System.out.println("Get value of apple ="+ map.get("apple"));
        System.out.println("Get value of pears ="+ map.get("pears"));
        System.out.println("Get value of pineapple ="+ map.get("pineapple"));
        System.out.println("Get value of peach ="+ map.get("peach"));
        map.remove("pears");
        System.out.println("Get value of pears ="+ map.get("pears"));
        System.out.println("Get value of mango ="+ map.get("mango"));
        System.out.println("Get value of apple ="+ map.get("apple"));
        System.out.println("Get value of peach ="+ map.get("peach"));
        System.out.println("Get value of orange ="+ map.get("orange"));
        map.remove("peach");


    }
}
