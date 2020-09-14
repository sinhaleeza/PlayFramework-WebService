for $x in doc("dblp-soc-papers-V2.xml")/dblp/article
order by $x/title
return $x/title[text()]


	