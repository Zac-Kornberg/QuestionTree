
public class QuestionNode {

	   public String data;
	   public QuestionNode yesNode;
	   public QuestionNode noNode;
	   

	   //Question Node
	   public QuestionNode(String data, QuestionNode yesNode, QuestionNode noNode) {
	      this.data = data;
	      this.yesNode = yesNode;
	      this.noNode = noNode; 
	   } 

	   //Answer Node
	   public QuestionNode(String data) {
		      this(data,null, null); 
		   }

	   //Check if the node is a Answer node
	    public boolean checkA(){
	        return (yesNode == null && noNode == null);
	    }

}
