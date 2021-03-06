#!/usr/bin/perl -w

$numReps = 36;  # must be a multiple of (n choose 2) where n is number of teams in player list

$teamList = `cat ../players.list`;
$playerList = "../temp.players.list";
@teams = split(/\s+/, $teamList);


$mapBaseDir = "..";
@maps = ('g3-traps.txt', 'g4-even.txt', 'g5-dmz.txt', 'g5-single.txt');
#@maps = ('g5-single.txt');

$teamNums = scalar @teams;
@playerNums = ($teamNums);
#@playerNums = (1, 2, $teamNums, 4*$teamNums);

@traderNums = ($teamNums);
#@tradeNums = (1, $teamNums/2, $teamNums);

@marbleNums = (20);
#@marbleNums = (4, 10, 20, 100);

$turnNums = 100;

sub mapSize {
    my $mapFile = shift;
    my $count = `wc $mapBaseDir/map/$mapFile`;
    my @wcinfo = split(/\s+/, $count);
    return int($wcinfo[1]/2) + 1;
}

sub genMatchups {
    my $teamNamesRef = shift;
    my @teamNames = @$teamNamesRef;
    my $matchupSize = shift;
    my $numTeams = shift;
    
    my @matchupList = ();
    
    if ($matchupSize == 1) {
        for $team (@teamNames) {
            my @list = ($team);
            push(@matchupList, \@list);
        }
    }
    elsif ($matchupSize == 2) {
        for $team1 (@teamNames){
            for $team2 (@teamNames) {
                if (not $team1 eq $team2) {
                    my @list = ($team1, $team2);
                    push (@matchupList, \@list);
                }
            }
        }
    }
    else {
        my $numEach = $matchupSize/$numTeams;
        my @list = ();
        for (my $i = 0; $i < $numEach; $i++) {
            push (@list, @teamNames);
        }
        push (@matchupList, \@list);
    }
    return @matchupList;
}

sub parseResults {
    my $totalRankRef = shift;
    my %totRank = %$totalRankRef;
    my $results = shift;

    @lines = split(/\n/, $results);
    for $line (@lines) {
        if ($line =~ /(\d): (.*):/) {
            $totRank{$2} += $1;
        }
        else {
            print "$results\n";
            last;
        }
            
    }
    
    return %totRank;
}

sub parseCounts {
    my $totalRankRef = shift;
    my %totRank = %$totalRankRef;
    my $results = shift;
    
    @lines = split(/\n/, $results);
    for $line (@lines) {
        if ($line =~ /(\d): (.*):/) {
            $totRank{$2}++;
        }
        else {
            print "$results\n";
            last;
        }
        
    }
    
    return %totRank;
}




%totalRanks = ();
for $playerNum (@playerNums) {
    $totalRanks{$playerNum} = {};
}
%totalRankCounts = ();
for $playerNum (@playerNums) {
    $totalRankCounts{$playerNum} = {};
}


%totalMapRanks = ();
for $map (@maps) {
    $totalMapRanks{$map} = {};
}
%totalMapRankCounts = ();
for $map (@maps) {
    $totalMapRankCounts{$map} = {};
}


%numReps = ();
%numMapReps = ();

for $map (@maps) {
    #$mapDim = mapSize($map);
    
    for $traderNum (@traderNums) {
        for $playerNum (@playerNums) {
            for $marblesNum (@marbleNums) {
                @matchups = genMatchups(\@teams, $playerNum, $teamNums);
                $numIters = $numReps/(scalar @matchups);
                for $matchupListRef (@matchups) {
                    if (-e $playerList) {
                        `rm $playerList`;
                    }
                    @matchupList = @$matchupListRef;
                    #print scalar @matchupList, "\n";
                    for $player (@matchupList) {
                        `echo $player >> $playerList`;
                    }
                    for ($i = 0; $i < $numIters; $i++) {
                        `java cell/sim/Cell 0 $marblesNum $traderNum $turnNums $mapBaseDir/map/$map $playerList 2> outfile`;
                        $result = `tail -$playerNum outfile`;
                        my $hashRef = $totalRanks{$playerNum};
                        %$hashRef = parseResults($hashRef, $result);
                        
                        my $hashRef2 = $totalRankCounts{$playerNum};
                        %$hashRef2 = parseCounts($hashRef2, $result);
                        
                        my $mapHashRef = $totalMapRanks{$map};
                        %$mapHashRef = parseResults($mapHashRef, $result);
                        
                        my $mapHashRef2 = $totalMapRankCounts{$map};
                        %$mapHashRef2 = parseCounts($mapHashRef2, $result);
                    
                        #print "\n\n$result\n";
                        print "Initial marbles: $marblesNum\nTraders: $traderNum\n";
                        print "Map: $map\n";
                        for $player (@matchupList) {
                            print "$player ";
                        }
                        print "\n\n";
                        $numReps{$playerNum}++;
                        $numMapReps{$map}++;
                    }
                }
            }
        }
    }
}


for $playerNum (keys %totalRanks) {
    my $hashRef = $totalRanks{$playerNum};
    my %totalRank = %$hashRef;
  
    my $hashRefCount = $totalRankCounts{$playerNum};
    my %totalRankCounts = %$hashRefCount;
    
    print "Results for games with $playerNum players:\n";
    for $group (sort {$totalRank{$a} <=> $totalRank{$b}} keys %totalRank) {
        print "$group: ", $totalRank{$group}/$totalRankCounts{$group}, "\n";
    }
    print "\n";
}


for $map (keys %totalMapRanks) {
    my $hashRef = $totalMapRanks{$map};
    my %totalRank = %$hashRef;
    
    my $hashRefCount = $totalMapRankCounts{$map};
    my %totalRankCounts = %$hashRefCount;
    
    print "Results for games with map $map:\n";
    for $group (sort {$totalRank{$a} <=> $totalRank{$b}} keys %totalRank) {
        print "$group: ", $totalRank{$group}/$totalRankCounts{$group}, "\n";
    }
    print "\n";
}

