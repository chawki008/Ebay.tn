package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.User;

public class UserDaoImpl  {
    private DAOFactory          daoFactory;
    private static final String SQL_SELECT_PAR_USERNAME = "SELECT id, email, password,first_name,last_name, date_inscription FROM user WHERE email = ?";
    private static final String SQL_INSERT = "INSERT INTO user (email, password, first_name,last_name, date_inscription) VALUES (?,?, ?,?, NOW())";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, email,password,first_name,last_name, date_inscription FROM user WHERE id = ?";
    private static final String SQL_BIG_BET = "Select * from user u , auction a where a.user_id=u.id and amount = ? and article_auction_id = ?";
    
    
    
	public UserDaoImpl(DAOFactory daoFactory) {
			this.daoFactory= daoFactory ;
	}

	

	
	public void creer(User user) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, user.getEmail(), user.getPassword(), user.getFirst_name(), user.getLast_name() );
	        int statut = preparedStatement.executeUpdate();
	       
	        if ( statut == 0 ) {
	            throw new DAOException( "�chec de la cr�ation de l'user, aucune ligne ajout�e dans la table." );
	        }
	        
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	          
	            user.setId( valeursAutoGenerees.getInt( 1 ) );
	        } else {
	            throw new DAOException( "�chec de la cr�ation de l'user en base, aucun ID auto-g�n�r� retourn�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }

	}

	
	public User get(String email) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_USERNAME, false, email );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	            user = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return user;
	}
	
	public static PreparedStatement initialisationRequetePreparee(
			Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets )throws SQLException 
	{
	    PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}
	
	private static User map( ResultSet resultSet ) throws SQLException {
	    User user = new User();
	    user.setId( resultSet.getInt( "id" ) );
	    user.setEmail( resultSet.getString( "email" ) );
	    user.setPassword( resultSet.getString( "password" ) );
	    user.setFirst_name(resultSet.getString( "first_name" ) );
	    user.setLast_name(resultSet.getString( "last_name" ) );
            user.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
	    return user;
	}
	public static void fermetureSilencieuse( ResultSet resultSet ) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "�chec de la fermeture du ResultSet : " + e.getMessage() );
	        }
	    }
	}

	/* Fermeture silencieuse du statement */
	public static void fermetureSilencieuse( Statement statement ) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "�chec de la fermeture du Statement : " + e.getMessage() );
	        }
	    }
	}

	/* Fermeture silencieuse de la connexion */
	public static void fermetureSilencieuse( Connection connexion ) {
	    if ( connexion != null ) {
	        try {
	            connexion.close();
	        } catch ( SQLException e ) {
	            System.out.println( "�chec de la fermeture de la connexion : " + e.getMessage() );
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



	
	public User find(int id) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	            user = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return user;
	}
        
        public User getBiggestBetter(int final_offer,int art_id) throws DAOException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_BIG_BET, false, final_offer,art_id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
	            user = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

	    return user;
	}
}
