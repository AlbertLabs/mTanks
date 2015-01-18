

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class test {
	
	static Firebase ref = new Firebase("https://incandescent-heat-5535.firebaseio.com/");

	public static void main(String[] args) {
		ref.setValue("hedddllo");
		
		
		ref.addValueEventListener(new ValueEventListener() {

			  @Override
			  public void onDataChange(DataSnapshot snapshot) {
			    System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
			  }

			  @Override public void onCancelled(FirebaseError error) { }
		});
		
		System.out.println("K");
		while(true);
	}
	


}
