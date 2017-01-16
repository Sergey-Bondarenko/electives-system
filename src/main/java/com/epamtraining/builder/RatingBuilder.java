package com.epamtraining.builder;

import com.epamtraining.entities.Account;
import com.epamtraining.entities.Rating;
import com.epamtraining.exception.BuildException;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Build a rating entity from request
 * @author Sergey Bondarenko
 */
public class RatingBuilder extends EntityBuilder<Rating> {

    private Logger logger = Logger.getRootLogger();
    @Override
    public Rating build(Map<String, String[]> map) throws BuildException {
        Rating rating = new Rating();
        build(map, rating);
        return rating;
    }

    /**
     * Fill entity
     * @param map
     * @param rating target rating
     * @throws BuildException
     */
    public void build(Map<String, String[]> map, Rating rating) throws BuildException{
        boolean ratingParam = !buildRating(map.get("rating"), rating);
        boolean comment = !buildComment(map.get("comment"), rating);

        if (ratingParam || comment){
            throw new BuildException();
        }
    }

    /**
     * Build rating
     * @param args parameter values
     * @param rating target rating
     */
    private boolean buildRating(String[] args, Rating rating) {
        if (args != null && args.length > 0){
            String ratingParam = args[0];
            if (ratingParam.length() > 0){
                try {
                    rating.setRating(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Build comment
     * @param args parameter values
     * @param rating target rating
     */
    private boolean buildComment(String[] args, Rating rating) {
        if (args != null && args.length > 0){
            String comment = args[0];
            if (comment.length() > 0){
                try {
                    rating.setComment(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e);
                }
                return true;
            }
        }
        return false;
    }
}
