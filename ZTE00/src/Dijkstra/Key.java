package Dijkstra;

public class Key {
   Integer start;
   Integer end;
   
   public Key(int start ,int end) {
	   this.start = start;
	   this.end = end;
   }

@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.start+"->"+this.end;
}

@Override
public boolean equals(Object obj) {
    boolean result = false;
    if (this == obj)
        result = true;
    if (obj == null || getClass() != obj.getClass())
        result = false;
    Key k = (Key) obj;
    if (k.start == null || k.end == null)
        result = false;
    if ((k.start.equals(start)) && (k.end.equals(end)))
        result = true;
    return result;

}

@Override
public int hashCode() {
    int a = 0;
    if (start != null && end != null) {
        a = start.hashCode() + end.hashCode();
    }
    return a;

}
   
  
}
