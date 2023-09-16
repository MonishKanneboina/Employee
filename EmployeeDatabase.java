public class EmployeeDatabase
{
   
   private class Entry
   {
      private int ID;
      private Employee employee;
   
      public Entry(int ID, Employee e)
      {
         this.ID = ID;
         employee = e;
      }
   
      @Override
      public String toString()
      {
         return ID + "" + employee.toString();
      }
     
   }
   
   private Entry[] entryA;
   private boolean collisionType;
   private int size;
   private int capacity;
   
   public EmployeeDatabase(boolean collisionType)
   {
      this.collisionType = collisionType;
      entryA = new Entry[11];
      size = 0;
      capacity = 11;
   }
   
   public int hashCode(int key)
   {
      return key % capacity;
   }
   
   public void put(int key, Employee e)
   {
      if(size <= capacity/2)
         {
            int suggested = hashCode(key);
            for (int counter = 1; entryA[suggested] != null; counter++)
               {
                  if(entryA[suggested].ID != key)
                     {
                        if (collisionType)
                           {
                              suggested = (suggested + 1)% capacity;
                           }
                        else 
                           {
                              suggested = (suggested + counter + counter*counter) % capacity;
                           }
                     }
                  else 
                     {
                        entryA[suggested] = null;
                     } 
               }
            entryA[suggested] = new Entry(key, e);
            size++; 
         }
         
      else
      {
         resizeAndRehash();
      }
   }
   
   
   
   public void resizeAndRehash()
   {
    Entry[] newEA = entryA;
    capacity = capacity * 2;
    entryA = new Entry[capacity];
    for(int i = 0; i < newEA.length; i++)
    {
      if(newEA[i] != null)
      {
        put(newEA[i].ID, newEA[i].employee);
      }
    }    
         
   }
   
   
public Employee get(int key)
  {
    int suggested = hashCode(key);
    if(entryA[suggested] != null)
    {
      int counter = 1;
      for (int checker = 0; checker != capacity; checker++)
      {
         if(entryA[suggested].ID != key)
          {
            if(collisionType)
            {         
              suggested = (suggested + 1)% capacity;
            }
          else 
             {
              suggested = (suggested + counter + counter*counter) % capacity;
              counter++;
             }
          }
            
         else
         {
            return entryA[suggested].employee;
         }
      }
   }
   return null;        
  }
}