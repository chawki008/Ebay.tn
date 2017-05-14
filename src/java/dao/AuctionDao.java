package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import beans.Auction ;


public class AuctionDao { 
private DAOFactory daoFactory;
private static final String SQL_INSERT = "INSERT INTO auction(amount, date,user_id,article_auction_id) Values (?, NOW(), ?,?)";
private static final String SQL_SELECT_PAR_ID = "SELECT * date FROM auction WHERE id = ? " ;
private static final String SQL_SELECT_PAR_DATE = "SELECT * FROM auction WHERE date= ? ";


public AuctionDao (DAOFactory daoFactory) {
 this.daoFactory = daoFactory;
}
public void create (Auction auction) throws DAOException{

 Connection connexion = null;
PreparedStatement preparedStatement = null;
ResultSet valeursAutoGenerees = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true , auction.getAmount(),auction.getBuyer().getId(),auction.getArticle().getId() );
	int statut = preparedStatement.executeUpdate();if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} valeursAutoGenerees = preparedStatement.getGeneratedKeys();
if ( valeursAutoGenerees.next() ) {
auction.setId( valeursAutoGenerees.getInt( 1 ) );
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

private static Auction map( ResultSet resultSet ) throws SQLException { 
 Auction auction = new Auction();
auction.setId(resultSet.getInt("id"));
auction.setAmount(resultSet.getInt("amount"));
auction.setDate(resultSet.getDate("date"));
auction.setArticle(DAOFactory.getInstance().getArticleDao().find_auc(resultSet.getInt("article_auction_id")));
auction.setBuyer(DAOFactory.getInstance().getUserDao().find(resultSet.getInt("user_id")));
return auction;}

 
public Auction find( int id) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Auction auction = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false,id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
auction = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return auction ;
}


public Auction find( String date) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Auction auction = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_DATE, false,date );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
auction = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return auction ;
}


 }