import java.io.Serializable;

public class CustomMap<K,V> implements Serializable {

  public Entry <K,V>[] finalMap ;
  private transient Node <K,V>[] table ;

  public CustomMap(){
      table = new Node[200];
  }

  public Entry<K,V>[] put(K key, V value){
      Node <K,V>[] entry;
      entry = table;
      if(key == null){
          return null;
      }
      int hash = this.generateHashCode(key);
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
     return putVal(entry);
  }

  public Entry<K,V>[] putVal(Node<K,V>[] map){
      finalMap = new Entry [map.length];
      int count = 0;
      for(int i =0; i< map.length;i++){
          if(map[i]!=null){
              finalMap[count] = new Entry(map[i].key,map[i].value);
              count++;
              Node<K,V> current = map[i].next;
              while(current != null) {
                  finalMap[count] = new Entry(current.key, current.value);
                    count++;
                    current=current.next;
                  }
          }
      }
      return finalMap;
  }
   public  int generateHashCode(Object key){
      if(key instanceof String){
          return ((String) key).length()+((String) key).charAt(0);
      }
       return (Integer)key;
   }

    public class Node<K,V>{
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value , Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }
    public class Entry<K,V>{
        public K key;
        public V value;

        public Entry(K key, V value){
            this.key = key;
            this.value = value;

        }

    }
    public V  get(K key){
      Node<K,V>[] entry;
      entry = table;
      if(key == null)
          return null;
      int hash = this.generateHashCode(key);
      if(entry[hash]!=null){
          if(entry[hash].next == null) {
              return entry[hash].value;
          }
          else{
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

   /* public static void main(String ...args){
      CustomMap map = new CustomMap();
      *//*map.put(1,"apple");
      map.put(2,"orange");
      map.put(3,"mango");
      map.put(1,"pineapple");*//*
    map.put("apple",2);
    map.put("orange",3);
    map.put("mango",4);
    map.put("pears",40);
    map.put("pineapple",5);
        System.out.println("Get value of orange ="+ map.get("orange"));
        System.out.println("Get value of mango ="+ map.get("mango"));
        System.out.println("Get value of apple ="+ map.get("apple"));
        System.out.println("Get value of pears ="+ map.get("pears"));



    }
*/}
