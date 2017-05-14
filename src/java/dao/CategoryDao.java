package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.Category;


public class CategoryDao { 
private DAOFactory daoFactory;
private static final String SQL_INSERT = "INSERT INTO category( name) Values (?)";
private static final String SQL_SELECT_PAR_ID = "SELECT id, name,father_id FROM category WHERE id = ? " ;
private static final String SQL_SELECT_PAR_NAME = "SELECT id, name,father_id FROM category WHERE name= ? ";
private static final String SQL_SELECT_CHILDREN = "SELECT id, name ,father_id FROM category WHERE father_id= ? ";
private static final String SQL_SELECT_ALL = "SELECT * FROM category  ";



public CategoryDao (DAOFactory daoFactory) {
 this.daoFactory = daoFactory;
}
public void create (Category category) throws DAOException{

 Connection connexion = null;
PreparedStatement preparedStatement = null;
ResultSet valeursAutoGenerees = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true , category.getName() );
	int statut = preparedStatement.executeUpdate();
        if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} 
        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
if ( valeursAutoGenerees.next() ) {
category.setId( valeursAutoGenerees.getInt( 1 ) );
} else {throw new DAOException( "Echec de la creation de l'user en base, aucun ID auto-genere retourne." );
}
 } catch ( SQLException e ) {
throw new DAOException( e ); 
} finally {
fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
}
}

public static PreparedStatement initialisationRequetePreparee(
 Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets )throws SQLException{

PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}
 
public static void fermetureSilencieuse( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Echec de la fermeture du ResultSet : " + e.getMessage());
	        }
	    }
	}

	/* Fermeture silencieuse du statement */
	public static void fermetureSilencieuse( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Echec de la fermeture du Statement : " + e.getMessage() );
	        }
	    }
	}

	/* Fermeture silencieuse de la connexion */
	public static void fermetureSilencieuse( Connection connexion ) {
	    if ( connexion != null ) {
	        try {
	            connexion.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Echec de la fermeture de la connexion : " + e.getMessage() );
	        }
	    }
	}

	/* Fermetures silencieuses du statement et de la connexion */
	public static void fermeturesSilencieuses( Statement statement, Connection connexion ) {
	    fermetureSilencieuse( statement );
	    fermetureSilencieuse( connexion );
	}

	/* Fermetures silencieuses du resultset, du statement et de la connexion */
	public static void fermeturesSilencieuses( ResultSet resultSet, Statement statement, Connection connexion ) {
	    fermetureSilencieuse( resultSet );
	    fermetureSilencieuse( statement );
	    fermetureSilencieuse( connexion );
	}

private static Category map( ResultSet resultSet ) throws SQLException { 
 Category category = new Category();
category.setId(resultSet.getInt("id"));
category.setName(resultSet.getString("name"));

return category;}

public List<Category> findChildren(Category category){
    Connection connexion = null;
PreparedStatement preparedStatement = null;
ResultSet resultSet = null;
    List <Category> categories;
    categories = new ArrayList <> ();
    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_CHILDREN, false,category.getId() );
	        resultSet = preparedStatement.executeQuery();
	        while ( resultSet.next() ) {
                    categories.add(map(resultSet));
                 }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
    
    return categories;
}



public Category find( int id) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Category category = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false,id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
                category = map(resultSet );
                            int f_id = resultSet.getInt("father_id"); 
                            if(f_id != 0)
                category.setParent(find(resultSet.getInt("father_id")));
            category.setChildren(findChildren(category));
}
            
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return category ;
}


public Category find( String name) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Category category = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_NAME, false,name );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
category = map(resultSet );
int f_id = resultSet.getInt("father_id"); 
            if(f_id != 0)
            category.setParent(find(f_id));
                   
            category.setChildren(findChildren(category));
            
}
            
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return category ;
}



public List<Category> findAll(){
       
    Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Category> Categories = new ArrayList <Category> ();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_ALL, true );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                Category category = map(resultSet);
                int f_id = resultSet.getInt("father_id"); 
                if(f_id != 0)
                category.setParent(find(resultSet.getInt("father_id")));
                category.setChildren(findChildren(category));
                Categories.add( category);
            }
        } catch ( SQLException e ) {
        	System.out.println(e.getMessage());
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return Categories;
    }
    
    


 }