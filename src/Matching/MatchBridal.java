package Matching;

import java.util.Random;

public class MatchBridal {
	int PeopleNum = 5; //男女の数
	int Man[] = new int[PeopleNum]; //男の数
	int Woman[] = new int[PeopleNum]; //女の数
	int proposeman[][] = new int[Man.length][Woman.length]; //プロポーズ表男性側　２は結婚　１はプロポーズ失敗か婚約破棄
	int ManQ[][] = new int[Man.length][Woman.length]; //男の選考表 中身には相手女性のデータ
	int WomanQ[][] = new int[Woman.length][Man.length]; //女の選考表　中身は相手男性
	int WomanQRev[][] = new int[Woman.length][Man.length]; //女の逆選考表 中身は相手男性
	int ManS[] = new int[Man.length]; //男性婚約表　Integer.MAX_VALUEだと婚約相手なし　中身は相手女性
	int WomanS[] = new int[Woman.length];//女性婚約表　Integer.MAX_VALUEだと婚約相手なし　中身は相手男性
	
	public MatchBridal () {
		MatchSystem();
	}
	
	private void ResetSheet () { //表のリセット
		for ( int i = 0; i < proposeman.length; i++ ) { //プロポーズ表のリセット
			for ( int j = 0; j < proposeman[0].length; j++ ) {
				proposeman[i][j] = 0;
			}
		}
		for ( int i = 0; i < ManS.length; i++ ) { //男性婚約表のリセット
				ManS[i] = Integer.MAX_VALUE;
		}
		for ( int i = 0; i < WomanS.length; i++ ) { //女性婚約表のリセット
			WomanS[i] = Integer.MAX_VALUE;
		}
	}
	private int[][] QSelect ( int[][] PeopleQ, int[] Peoplepartner ) { //選考表をつくるよ
		Random rnd = new Random(); //Randomクラスのインスタンス化
		for ( int i = 0; i < PeopleQ.length; i++ ) { //男性選考表への代入
            int Q[] = new int[Peoplepartner.length]; //選考表をつくるための乱数用配列
            for ( int k = 0; k < Q.length; k++ ) { //まずはマックスバリュー代入で初期化
                Q[k] = Integer.MAX_VALUE;
            }
            int flug = 1; //フラグを用意
            for ( int k = 0; k < Q.length; k++ ) { //乱数用配列を作る
            	do {
            		Q[k] = rnd.nextInt(Peoplepartner.length); //乱数生成
            		for ( int q = 0; q < Q.length; q++ ) { //このとき既にだした相手じゃないかを確認用
            			if ( Q[k] == Q[q] && k != q ) {
            				flug = 0; //iとjが違うとき（つまり自己参照以外で）同じ者があったらもっかいヤレフラグをたてる
            				break;
            			}
        				else flug = 1;
            		}	
            	}
            	while ( flug == 0 ); //フラグ0ならやれ
            	flug = 1;//フラグを戻す
            }
        	for ( int j = 0; j < PeopleQ[0].length; j++ ) { //最後の代入
        		PeopleQ[i][j] = Q[j];
        	}       	
        }
		return PeopleQ;
	}
	private int[][] RevMaker ( int[][] peopleQ, int[][] QRev ) { //逆選考表をつくるよ
		for ( int i = 0; i < peopleQ.length; i++ ) {
			for ( int j = 0; j < peopleQ[0].length; j++ ) {
				QRev[i][ peopleQ[i][j] ] = j;
			}
		}
		return QRev;
	}
	private void QView ( int[][] View ) { //選考表の表示
		for ( int i = 0; i < View.length; i++ ) {
			System.out.print ( i + " {\t" );
			for ( int j = 0; j < View[0].length; j++ ) {
				System.out.print( View[i][j] + "\t" );
			}
			System.out.println("}");
		}
		System.out.println();
	}
	private void LastView( int[] Partner ) { //最終結婚相手表示
		for ( int i = 0; i < Partner.length; i++ ) {
			System.out.println( i + " の結婚相手は " + Partner[i] );
		}
	}
	public void MatchSystem () { //マッチシステム
		ResetSheet();
		ManQ = QSelect( ManQ, Woman );
		WomanQ = QSelect ( WomanQ, Man );
		WomanQRev = RevMaker ( WomanQ, WomanQRev );
		QView ( ManQ );
		QView ( WomanQ );
		QView ( WomanQRev );
		int PartnerJ = 0; //第1希望のパートナーを選ぶ変数
		int flug = 1; //終了フラグ
		do {
			flug = 0; //フラグは折っておく
			for ( int i = 0; i < Man.length; i++ ) {
				if ( ManS[i] == Integer.MAX_VALUE ) { //結婚していないとき、結婚相手を探す
					if ( proposeman[i][ ManQ[i][PartnerJ] ] == 0 ) { //対象にプロポーズしているかを調べる、プロポーズしていない場合
						if ( WomanS[ManQ[i][PartnerJ]] == Integer.MAX_VALUE ) { //対象が未婚
							WomanS[ManQ[i][PartnerJ]] = i; //iの人が一番結婚したい女性が独身ならばiの人と結婚する
							ManS[i] = ManQ[i][PartnerJ]; //希望女性と結婚と書き込む
							proposeman[i][ ManQ[i][PartnerJ] ] = 2;//プロポーズ表を更新する 2だと結婚
						}
						else if ( WomanS[ ManQ[i][PartnerJ] ] < Integer.MAX_VALUE ) { //対象の女性が既に結婚していたら
								if ( WomanQRev[ ManQ[i][PartnerJ] ][i] > WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //逆選考表の対象女性にとってiの人が上か、それとも今の結婚相手のが上かどうか
									proposeman[i][ ManQ[i][PartnerJ] ] = 1; //すでに結婚してる人のほうが好きなとき　は嫌われたのでプロポーズだけ
								}
								else if ( WomanQRev[ ManQ[i][PartnerJ] ][i] < WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //新しい人のが好き
									proposeman[ WomanS[ ManQ[i][PartnerJ] ] ][ ManQ[i][PartnerJ] ] = 1; //婚約破棄
									ManS [ WomanS[ ManQ[i][PartnerJ] ] ] = Integer.MAX_VALUE; //二人ともいったん未婚に
									WomanS[ ManQ[i][0] ] = Integer.MAX_VALUE;
									proposeman[i][ ManQ[i][PartnerJ] ] = 2; //新しい結婚
									ManS[i] = ManQ[i][PartnerJ]; //お互いの結婚相手を変更
									WomanS[ManQ[i][PartnerJ]] = i;
								}					
						}
					}
					else if ( proposeman[i][ ManQ[i][PartnerJ] ] > 0 ) { //対象がプロポーズしていたり結婚していた場合
						PartnerJ += 1;
						if ( WomanS[ManQ[i][PartnerJ]] == Integer.MAX_VALUE ) { //対象が未婚
							WomanS[ManQ[i][PartnerJ]] = i; //iの人が一番結婚したい女性が独身ならばiの人と結婚する
							ManS[i] = ManQ[i][PartnerJ]; //希望女性と結婚と書き込む
							proposeman[i][ ManQ[i][PartnerJ] ] = 2;//プロポーズ表を更新する 2だと結婚
						}
						else if ( WomanS[ ManQ[i][PartnerJ] ] < Integer.MAX_VALUE ) { //対象の女性が既に結婚していたら
								if ( WomanQRev[ ManQ[i][PartnerJ] ][i] > WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //逆選考表の対象女性にとってiの人が上か、それとも今の結婚相手のが上かどうか
									proposeman[i][ ManQ[i][PartnerJ] ] = 1; //すでに結婚してる人のほうが好きなとき　は嫌われたのでプロポーズだけ
								}
								else if ( WomanQRev[ ManQ[i][PartnerJ] ][i] < WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //新しい人のが好き
									proposeman[ WomanS[ ManQ[i][PartnerJ] ] ][ ManQ[i][PartnerJ] ] = 1; //婚約破棄
									ManS [ WomanS[ ManQ[i][PartnerJ] ] ] = Integer.MAX_VALUE; //二人ともいったん未婚に
									WomanS[ ManQ[i][0] ] = Integer.MAX_VALUE;
									proposeman[i][ ManQ[i][PartnerJ] ] = 2; //新しい結婚
									ManS[i] = ManQ[i][PartnerJ]; //お互いの結婚相手を変更
									WomanS[ManQ[i][PartnerJ]] = i;
								}					
						}
					}
				}
			}
			for ( int i = 0; i < ManS.length; i++ ) { //フラグ処理
				if ( ManS[i] == Integer.MAX_VALUE ) flug = 1;
			}
		} while ( flug == 1 ); //未婚約の人を処理
		
		LastView ( ManS );
		QView ( proposeman );
	}
}
