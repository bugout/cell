#!/usr/bin/perl

sub mapSize {
    my $mapFile = shift;
    my $count = `wc ../map/$mapFile`;
    my @wcinfo = split(/\s+/, $count);
    return int($wcinfo[1]/2) + 1;
}

sub genMatchups {
    my $teamNamesRef = shift;
    my @teamNames = @$teamNamesRef;
    my $matchupSize = shift;
    
    my @matchupList = ();
    
    if ($matchupSize == 1) {
        for $team (@teamNames) {
            my @list = ($team);
            push(@matchupList, \@list);
        }
    }
    if ($matchupSize == 2) {
        for $team1 (@teamNames){
            for $team2 (@teamNames) {
                if (not $team1 eq $team2) {
                    my @list = ($team1, $team2);
                    push (@matchupList, \@list);
                }
            }
        }
    }
    if ($matchupSize == 5 || $matchupSize == 3) {
        push (@matchupList, \@teamNames);
    }
    if ($matchupSize == 20) {
        my @list = ();
        push (@list, @teamNames);
        push (@list, @teamNames);
        push (@list, @teamNames);
        push (@list, @teamNames);
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
        if ($line =~ /(\d): (\w+):/) {
            $totRank{$2} += $1;
        }
        else {
            print "$results\n";
            last;
        }
            
    }
    
    return %totRank;
}


$numReps = 20;

$teamList = `cat ../players.list`;
$playerList = "../temp.players.list";
@teams = split(/\s+/, $teamList);

#@maps = ('g3-traps.txt', 'g5-single.txt', 'g0-rotate.txt', 'g4-concentric.txt', 'g4-even.txt', 'g4-explosion-big.txt', 'g4-wedges.txt', 'g3-even.txt');

@maps = ('g3-traps.txt');

#@turnNums = (100, 200);
@turnNums = (100);

#@traderNums = (1, 2, 5, 10);
@traderNums = (5);

#@playerNums = (1, 2, 5, 20);
@playerNums = (3);

%totalRank = ();

for $map (@maps) {
    $mapDim = mapSize($map);
    #print "$mapDim\n";
    for $turnNum (@turnNums) {
        for $traderNum (@traderNums) {
            for $playerNum (@playerNums) {
                $marblesNum = int($mapDim*20/$traderNum);
                @matchups = genMatchups(\@teams, $playerNum);
                #$numIters = $numReps/(scalar @matchups);
                for $matchupListRef (@matchups) {
                    if (-e $playerList) {
                        `rm $playerList`;
                    }
                    @matchupList = @$matchupListRef;
                    for $player (@matchupList) {
                        `echo $player >> $playerList`;
                    }
                    for ($i = 0; $i < $numReps; $i++) {
                        `java cell/sim/Cell 0 $marblesNum $traderNum $turnNum ../map/$map $playerList 2> outfile`;
                        $result = `tail -$playerNum outfile`;
                        %totalRank = parseResults(\%totalRank, $result);
                        
                        
                        #print "\n\n$result\n";
                        #print "Marbles: $marblesNum\nTraders: $traderNum\nTurns: $turnNum\n";
                        #print "Map: $map\n";
                        #for $player (@matchupList) {
                        #    print "$player ";
                        #}
                        #print "\n";
                    }
                }
            }
        
        }
    }
}

$totalReps = $numReps * (scalar @maps);

for $group (sort {$totalRank{$a} <=> $totalRank{$b}} keys %totalRank) {
    print "$group: ", $totalRank{$group}/$totalReps, "\n";
}

