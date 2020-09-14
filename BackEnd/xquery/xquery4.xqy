declare variable $paperName as xs:string external;
for $x in doc("dblp-soc-papers-V2.xml")/dblp/*
where $x//title = $paperName
return $x