declare variable $author as xs:string external;
declare variable $year as xs:int external;
for $x in doc("dblp-soc-papers-V2.xml")/dblp/article[author=$author and year=$year]
order by $x/title
return $x/title