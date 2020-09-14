let $authorsList := for $x in doc("dblp-soc-papers-V2.xml")/dblp//author
                    order by $x
                    return $x
let $numAuthors := count($authorsList)
for $author in $authorsList
where count(doc("dblp-soc-papers-V2.xml")/dblp//author[text()=$author]) > 10
return $author