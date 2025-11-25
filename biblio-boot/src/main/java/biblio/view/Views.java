package biblio.view;

public class Views {

	public class Common {} 
	
	public class editeurWithLivres extends Editeur{}

	public class Auteur extends Common{}

	public class Editeur extends Common{}

	public class Collection extends Common{}
	
	public class Personne extends Common{}

	public class collectionWithLivres extends Collection{}

	public class auteurWithLivres extends Auteur{}

	public class Livre extends Common{}
	
	//public class livreWithAuteur extends Livre{}

	//public class livreWithEditeur extends Livre{}

	//public class livreWithCollection extends Livre{}
}
