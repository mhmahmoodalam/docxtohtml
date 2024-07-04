# Docx to html converter

# Version 5
- not all numbering should be unordered list maintain the order if decimal ordering present

# Version 4
- Not all comments are popup some are just generic comment
- handle those comments by ignoring them

# Version 3
- Handle Brand name to be replaced by a placeholder
- handle names in each language using a list of texts that correspond to a placeholder {0} for brand name

# Version 2
- handle embedded comments
- allow comments to be added as a pop element with specific texts having links to these comments
- eliminate repeative enclosing tags

# Version 1
- Run through the  docx file and convert to html plain file
- convert all list to unordered dash list



Refernce Doc

- [Sprinng boot upload file example](https://spring.io/guides/gs/uploading-files)
- [How to configure h2 database](https://www.baeldung.com/spring-boot-h2-database)
- [spring boot store file in db](https://www.baeldung.com/java-db-storing-files)
- [Flyway migration](https://www.baeldung.com/database-migrations-with-flyway)
- [open api documention](https://www.baeldung.com/spring-rest-openapi-documentation)
- [hibernate join column](https://www.baeldung.com/jpa-join-column)
- doenload zip file (https://yatmanwong.medium.com/aug-1-placeholder-f941f532f507)
- download zip file (https://www.springcloud.io/post/2023-03/springboot-download/#gsc.tab=0)

local links
- http://localhost:8080/h2-console
- http://localhost:8080/swagger-ui


Status
- Mock Get and Post call-
  - test File System upload
  - test reading and deleteing file system
  -
  - implemenet job creation
  - implement listing jobs
  - upload zip
  - unzip zip file
- next
  - allow conversion for jobname
  - allow downloading file after conversion
  - allow cleanup after job
  - 