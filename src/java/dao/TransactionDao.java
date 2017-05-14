package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.Transaction;


public class TransactionDao { 
private final DAOFactory daoFactory;
private static final String SQL_INSERT = "INSERT INTO transaction(quantity,user_id,article_id, date) Values (?,?, ?, NOW())";
private static final String SQL_SELECT_PAR_ID = "SELECT id, quantity, date FROM transaction WHERE id = ? " ;
private static final String SQL_SELECT_PAR_DATE = "SELECT id, quantity, date FROM transaction WHERE date= ? ";
private static final String SQL_SELECT_ALL = "Select * From transaction where user_id = ?";

public TransactionDao (DAOFactory daoFactory) {
 this.daoFactory = daoFactory;
}
public void create (Transaction transaction) throws DAOException{

 Connection connexion = null;
PreparedStatement preparedStatement = null;
ResultSet valeursAutoGenerees = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true , transaction.getQuantity(),transaction.getBuyer().getId(),transaction.getArticle().getId());
	int statut = preparedStatement.executeUpdate();if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} valeursAutoGenerees = preparedStatement.getGeneratedKeys();
if ( valeursAutoGenerees.next() ) {
transaction.setId( valeursAutoGenerees.getInt( 1 ) );
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
	            System.out.println( "Echec de la fermeture du ResultSet : " + e.getMessage() );
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

private static Transaction map( ResultSet resultSet ) throws SQLException { 
 Transaction transaction = new Transaction();
transaction.setId(resultSet.getInt("id"));
transaction.setQuantity(resultSet.getInt("quantity"));
transaction.setDate(resultSet.getDate("date"));
return transaction;}

 
public Transaction find( int id) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Transaction transaction = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false,id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
transaction = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return transaction ;
}


public Transaction find( String date) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Transaction transaction = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_DATE, false,date );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
transaction = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return transaction ;
}
public List<Transaction> findAll(int user_id){
       
    Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList <> ();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_ALL, true , user_id );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                Transaction transaction = map(resultSet);
                transaction.setBuyer(daoFactory.getUserDao().find(user_id));
                transaction.setArticle(daoFactory.getArticleDao().find(resultSet.getInt("article_id")));   
                transactions.add(transaction);
                
            }
        } catch ( SQLException e ) {
        	System.out.println(e.getMessage());
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return transactions;
    }


 }