package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import beans.Article;
import beans.ArticleAuction;
import beans.Category;
import beans.User;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class ArticleDao { 
private final DAOFactory daoFactory;
private static final String SQL_INSERT = "INSERT INTO article( price, quantity, art_condition, shipping, brand, description, name,user_id,category_id,type,img_ext,sent) Values (?,?,?,?,?,?, ?, ?, ?, ?, ?, ?)";
private static final String SQL_SELECT_PAR_ID = "SELECT * FROM article WHERE id = ? " ;
private static final String SQL_SELECT_PAR_NAME = "SELECT * FROM article WHERE name = ? ";
private static final String SQL_INSERT_AUC = "INSERT INTO article_auction( id,date_limit,final_offer) Values (?,?,?)";
private static final String SQL_SELECT_AUC_PAR_ID = "SELECT date_limit ,final_offer FROM article_auction WHERE id = ? " ;
    private static final String SQL_SELECT_ALL = "SELECT * FROM article where sent = 0" ;
private static final String SQL_SELECT_BY_CAT = "SELECT * FROM article WHERE category_id = ? and sent = 0" ;
private static final String SQL_UPDATE ="UPDATE article SET price = ?,quantity = ?,art_condition=?,shipping=?,brand=?,description=?,name = ?,user_id = ?,category_id = ? ,sent=? WHERE id = ?" ;


public ArticleDao (DAOFactory daoFactory) {
 this.daoFactory = daoFactory;
}
public Article create (Article article ) throws DAOException{
    return create(article, false);
}
public Article create (Article article ,boolean type) throws DAOException{

 Connection connexion = null;
PreparedStatement preparedStatement = null;
ResultSet valeursAutoGenerees = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true , article.getPrice(), article.getQuantity(), article.getCondition(), article.isShipping(), article.getBrand(), article.getDescription(), article.getName(),article.getOwner().getId(),article.getCategory().getId() ,type,article.getImg_extension(),article.isSent());
	int statut = preparedStatement.executeUpdate();if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} valeursAutoGenerees = preparedStatement.getGeneratedKeys();
if ( valeursAutoGenerees.next() ) {
article.setId( valeursAutoGenerees.getInt( 1 ) );
} else {throw new DAOException( "Echec de la creation de l'user en base, aucun ID auto-genere retourne." );
}
 } catch ( SQLException e ) {
throw new DAOException( e ); 
} finally {
fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
}
return article;
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

private static Article map( ResultSet resultSet ) throws SQLException { 
 Article article = new Article();
article.setId(resultSet.getInt("id"));
article.setPrice(resultSet.getInt("price"));
article.setQuantity(resultSet.getInt("quantity"));
article.setCondition(resultSet.getString("art_condition"));
article.setShipping(resultSet.getBoolean("shipping"));
article.setSent(resultSet.getBoolean("sent"));
article.setBrand(resultSet.getString("brand"));
article.setDescription(resultSet.getString("description"));
article.setName(resultSet.getString("name"));
article.setImg_extension(resultSet.getString("img_ext"));
User owner = DAOFactory.getInstance().getUserDao().find(resultSet.getInt("user_id"));
Category  category = DAOFactory.getInstance().getCategoryDao().find(resultSet.getInt("category_id"));
article.setCategory(category);
article.setOwner(owner);
article.setAuc(resultSet.getBoolean("type"));
return article;}

public void save(ArticleAuction article){
    Connection connexion = null;
PreparedStatement preparedStatement = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE, true , article.getPrice(), article.getQuantity(), article.getCondition(), article.isShipping(), article.getBrand(), article.getDescription(), article.getName(),article.getOwner().getId(),article.getCategory().getId() ,article.isSent(),article.getId());
	int statut = preparedStatement.executeUpdate();
        if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} 
        PreparedStatement prepar = null;
        prepar = initialisationRequetePreparee( connexion,"UPDATE article_auction set final_offer = ? where id = ?",true,article.getFinal_auction(),article.getId());
        prepar.executeUpdate();
 } catch ( SQLException e ) {
throw new DAOException( e ); 
} finally {
fermeturesSilencieuses(  preparedStatement, connexion );
}
}
public void save_art(Article article){
    Connection connexion = null;
PreparedStatement preparedStatement = null;

try { 
  connexion = daoFactory.getConnection();
preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE, true , article.getPrice(), article.getQuantity(), article.getCondition(), article.isShipping(), article.getBrand(), article.getDescription(), article.getName(),article.getOwner().getId(),article.getCategory().getId() ,article.isSent(),article.getId());
	int statut = preparedStatement.executeUpdate();
        if ( statut == 0 ) {
throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} 
        
 } catch ( SQLException e ) {
throw new DAOException( e ); 
} finally {
fermeturesSilencieuses(  preparedStatement, connexion );
}
}
 
public Article find( int id) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Article article = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
article = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return article ;
}


public Article find( String name) throws DAOException {Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Article article = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_NAME, false, name );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
article = map(resultSet );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }
return article ;
}

public void create_auc(ArticleAuction article){
    Date date = new java.sql.Date(article.getDate_limit().getTime());
    int final_offer = article.getFinal_auction();
    article.setId(create(article,true).getId());
    Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try { 
          connexion = daoFactory.getConnection();
        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT_AUC, true , article.getId(),date,final_offer );
                int statut = preparedStatement.executeUpdate();if ( statut == 0 ) {
        throw new DAOException( "Echec de la creation de l'user, aucune ligne ajoutee dans la table." );} valeursAutoGenerees = preparedStatement.getGeneratedKeys();
        
         } catch ( SQLException e ) {
        throw new DAOException( e ); 
        } finally {
        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    
}
    public ArticleAuction map_auc(ResultSet resultSet, Article article)throws SQLException{
        ArticleAuction article_auc = new ArticleAuction();
        article_auc.setDate_limit(resultSet.getDate("date_limit"));
        article_auc.setFinal_auction(resultSet.getInt("final_offer"));
        article_auc.setBrand(article.getBrand());
        article_auc.setCategory(article.getCategory());
        article_auc.setCondition(article.getCondition());
        article_auc.setDescription(article.getDescription());
        article_auc.setName(article.getName());
        article_auc.setOwner(article.getOwner());
        article_auc.setId(article.getId());
        article_auc.setPrice(article.getPrice());
        article_auc.setQuantity(article.getQuantity());
        article_auc.setShipping(article.isShipping());
        article_auc.setBrand(article.getBrand());
        article_auc.setImg_extension(article.getImg_extension());
        article_auc.setAuc(true);
        article_auc.setSent(article.isSent());
        return article_auc;
        
    }

    public ArticleAuction find_auc(int id){
        ArticleAuction article_auc = null;
        Article article = find(id);
        Connection connexion = null;
         PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_AUC_PAR_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        if ( resultSet.next() ) {
        article_auc = map_auc(resultSet,article );
}
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	    }

        
        return article_auc;
    }
    public ArticleAuction find_auc(String name){
        Article article = find(name);
        return find_auc(article.getId());
    }
    
    public List<Article> findAll(){
       
    Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Article> Articles = new ArrayList <Article> ();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_ALL, true );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                Articles.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
        	System.out.println(e.getMessage());
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return Articles;
    }
    
    public List<Article> findByCategory(Category category){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Article> Articles = new ArrayList <Article> ();
        int cat_id = category.getId();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connection, SQL_SELECT_BY_CAT, true,cat_id );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
                Articles.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
        	System.out.println(e.getMessage());
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return Articles;
        
    }
 }