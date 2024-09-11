#COMMANDS:

uci.command.uci=uci

uci.command.debug=debug {0}
uci.command.debug.values={on,off}

uci.command.isready=isready

#ex: "setoption name Nullmove value true\n"
uci.command.setOptionWithValue=setoption name {0} value {1}

#ex: "setoption name Clear Hash\n"
uci.command.setOptionWithoutValue=setoption name {0}

#"register later"
#"register name Stefan MK code 4359874324"
uci.command.register.values={later,name,code}
uci.command.register=register


uci.newGame=ucinewgame

#ex:"position startpos"
#ex:"position fen 2kr3r/p2b1R2/7p/1p1p2p1/1Pp5/N3P1P1/P5PP/6K1 b - - 0 23"
#ex:"position startpos e2e4 d7d5"
uci.command.setPosition=position {0}
uci.command.setPositionWithMoves=position {0} moves {1}
uci.command.setPositionValues={fen,startpos}

#ex: "go infinite searchmoves e2e4 d2d4"
#ex: "go depth 21"
uci.command.go=go
uci.command.go.searchmoves=searchmoves {0}
uci.command.go.ponder=ponder
uci.command.go.wtime=wtime {0} #milliseconds
uci.command.go.btime=btime {0} #milliseconds
uci.command.go.winc=winc #milliseconds
uci.command.go.binc=binc #milliseconds
uci.command.go.movestogo=movestogo
uci.command.go.depth=depth
uci.command.go.nodes=nodes 
uci.command.go.mate=mate 
uci.command.go.movetime=movetime 
uci.command.go.infiniteinfinite
    
uci.command.stop=stop

uci.command.ponderhit=ponderhit

uci.command.quit=quit


#RESPONSES:

uci.response.id=id
uci.response.id.values={name,author}

uci.response.uci=uciok

uci.response.ready=readyok

uci.response.bestmove=bestmove

uci.response.copyprotection=copyprotection
         
uci.response.registration
	      
uci.response.info=info
uci.response.info.additional.depth=depth
uci.response.info.additional.seldepth=seldepth
uci.response.info.additional.time=time
uci.response.info.additional.nodes=nodes
uci.response.info.additional.pv=pv
uci.response.info.additional.multipv=multipv 
uci.response.info.additional.score=score
uci.response.info.additional.score.cp=cp
uci.response.info.additional.score.mate=mate
uci.response.info.additional.score.lowerbound=lowerbound
uci.response.info.additional.score.upperbound=upperbound
uci.response.info.additional.currmove=currmove
uci.response.info.additional.currmovenumber=currmovenumber
uci.response.info.additional.hashfull=hashfull
uci.response.info.additional.nps=nps
uci.response.info.additional.tbhits=tbhits
uci.response.info.additional.tbhits=tbhits
uci.response.info.additional.string=string
uci.response.info.additional.refutation=refutation
uci.response.info.additional.currline=currline

#ex:"option name Nullmove type check default true\n"
#ex:"option name Selectivity type spin default 2 min 0 max 4\n"
#ex:"option name Style type combo default Normal var Solid var Normal var Risky\n"
#ex:"option name NalimovPath type string default c:\\n"
#ex:"option name Clear Hash type button\n"
uci.response.option=option
uci.response.option.name=name
uci.response.option.name.values={Hash,NalimovPath,NalimovCache,Ponder,OwnBook,MultiPV,UCI_ShowCurrLine,UCI_ShowRefutations,UCI_LimitStrength,UCI_Elo,UCI_AnalyseMode,UCI_Opponent}
uci.response.option.type=type
uci.response.option.type.values={check,spin,combo,button,string}
uci.response.option.default=default
uci.response.option.min=min
uci.response.option.max=max
uci.response.option.var=var