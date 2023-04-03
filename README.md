# Assignment02
For sample-result.pdf, I am not sure what I am supposed to do for that so I am just going to submit some example results from my side.

zip 94108 summary leads to...
94108 Business Summary
Total Businesses: 2001
Business Types: 17
Neighborhood: 37

naics 4855 summary leads to...
Total Businesses: 1364
Zip Codes: 302
Neighborhood: 38


Also, my implementation might be different from others since I will use the line of the csv file even if one of the factors is not there. For example, if the zip code is missing, I will still create a node for the naics if it exists, but there will just not be a zip code attached to that specific range.

Also, for new business last year, I was not sure whether or not to increase the count if it closes in the same year so I decided to not increment if it closed the same year.
