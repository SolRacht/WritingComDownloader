CREATE TABLE CHAPTERS (
	story_id VARCHAR 	not null,
	descent  VARCHAR 	not null,
	author   VARCHAR	    	   ,
	title    VARCHAR 	not null   ,
	body     text    	not null   ,
	choices_cs varchar	           , date_created TIMESTAMP,
	PRIMARY KEY (Story_id, descent)
);

CREATE TABLE STORIES (
	story_id varchar primary key,
	title    varchar

);
