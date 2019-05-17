# Football-results-generator
<p> The program reads the match data from text file (Invalid.txt, valid.txt) and validates if following errors are present:</p>
<ul>
  <li>The home team name may be missing. </li>
  <li>The away team name may be missing. </li>
  <li>The home team score may be missing. </li>
  <li>The away team score may be missing</li>
  <li>The field delimiter may be missing or wrong field delimiter is used.	</li>
  <li>Home team score may not be a valid integer number. </li>
  <li>Away team score may not be a valid integer number.</li>
</ul>
<p> And, skips the lines with errors and prints lines without errors in following format: </p>
<p>
      Home team  	 	Score    	Away team     Score
      =========		  =====		=========		    ======
      Arsenal 		    2 		Spurs	 		        1
      Everton 		    1 		Liverpool 		    1
      Huddersfield	  2 		Chelsea 		      1

</p>
