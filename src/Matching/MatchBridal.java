package Matching;

import java.util.Random;

public class MatchBridal {
	int PeopleNum = 5; //�j���̐�
	int Man[] = new int[PeopleNum]; //�j�̐�
	int Woman[] = new int[PeopleNum]; //���̐�
	int proposeman[][] = new int[Man.length][Woman.length]; //�v���|�[�Y�\�j�����@�Q�͌����@�P�̓v���|�[�Y���s������j��
	int ManQ[][] = new int[Man.length][Woman.length]; //�j�̑I�l�\ ���g�ɂ͑��菗���̃f�[�^
	int WomanQ[][] = new int[Woman.length][Man.length]; //���̑I�l�\�@���g�͑���j��
	int WomanQRev[][] = new int[Woman.length][Man.length]; //���̋t�I�l�\ ���g�͑���j��
	int ManS[] = new int[Man.length]; //�j������\�@Integer.MAX_VALUE���ƍ��񑊎�Ȃ��@���g�͑��菗��
	int WomanS[] = new int[Woman.length];//��������\�@Integer.MAX_VALUE���ƍ��񑊎�Ȃ��@���g�͑���j��
	
	public MatchBridal () {
		MatchSystem();
	}
	
	private void ResetSheet () { //�\�̃��Z�b�g
		for ( int i = 0; i < proposeman.length; i++ ) { //�v���|�[�Y�\�̃��Z�b�g
			for ( int j = 0; j < proposeman[0].length; j++ ) {
				proposeman[i][j] = 0;
			}
		}
		for ( int i = 0; i < ManS.length; i++ ) { //�j������\�̃��Z�b�g
				ManS[i] = Integer.MAX_VALUE;
		}
		for ( int i = 0; i < WomanS.length; i++ ) { //��������\�̃��Z�b�g
			WomanS[i] = Integer.MAX_VALUE;
		}
	}
	private int[][] QSelect ( int[][] PeopleQ, int[] Peoplepartner ) { //�I�l�\�������
		Random rnd = new Random(); //Random�N���X�̃C���X�^���X��
		for ( int i = 0; i < PeopleQ.length; i++ ) { //�j���I�l�\�ւ̑��
            int Q[] = new int[Peoplepartner.length]; //�I�l�\�����邽�߂̗����p�z��
            for ( int k = 0; k < Q.length; k++ ) { //�܂��̓}�b�N�X�o�����[����ŏ�����
                Q[k] = Integer.MAX_VALUE;
            }
            int flug = 1; //�t���O��p��
            for ( int k = 0; k < Q.length; k++ ) { //�����p�z������
            	do {
            		Q[k] = rnd.nextInt(Peoplepartner.length); //��������
            		for ( int q = 0; q < Q.length; q++ ) { //���̂Ƃ����ɂ��������肶��Ȃ������m�F�p
            			if ( Q[k] == Q[q] && k != q ) {
            				flug = 0; //i��j���Ⴄ�Ƃ��i�܂莩�ȎQ�ƈȊO�Łj�����҂���������������������t���O�����Ă�
            				break;
            			}
        				else flug = 1;
            		}	
            	}
            	while ( flug == 0 ); //�t���O0�Ȃ���
            	flug = 1;//�t���O��߂�
            }
        	for ( int j = 0; j < PeopleQ[0].length; j++ ) { //�Ō�̑��
        		PeopleQ[i][j] = Q[j];
        	}       	
        }
		return PeopleQ;
	}
	private int[][] RevMaker ( int[][] peopleQ, int[][] QRev ) { //�t�I�l�\�������
		for ( int i = 0; i < peopleQ.length; i++ ) {
			for ( int j = 0; j < peopleQ[0].length; j++ ) {
				QRev[i][ peopleQ[i][j] ] = j;
			}
		}
		return QRev;
	}
	private void QView ( int[][] View ) { //�I�l�\�̕\��
		for ( int i = 0; i < View.length; i++ ) {
			System.out.print ( i + " {\t" );
			for ( int j = 0; j < View[0].length; j++ ) {
				System.out.print( View[i][j] + "\t" );
			}
			System.out.println("}");
		}
		System.out.println();
	}
	private void LastView( int[] Partner ) { //�ŏI��������\��
		for ( int i = 0; i < Partner.length; i++ ) {
			System.out.println( i + " �̌�������� " + Partner[i] );
		}
	}
	public void MatchSystem () { //�}�b�`�V�X�e��
		ResetSheet();
		ManQ = QSelect( ManQ, Woman );
		WomanQ = QSelect ( WomanQ, Man );
		WomanQRev = RevMaker ( WomanQ, WomanQRev );
		QView ( ManQ );
		QView ( WomanQ );
		QView ( WomanQRev );
		int PartnerJ = 0; //��1��]�̃p�[�g�i�[��I�ԕϐ�
		int flug = 1; //�I���t���O
		do {
			flug = 0; //�t���O�͐܂��Ă���
			for ( int i = 0; i < Man.length; i++ ) {
				if ( ManS[i] == Integer.MAX_VALUE ) { //�������Ă��Ȃ��Ƃ��A���������T��
					if ( proposeman[i][ ManQ[i][PartnerJ] ] == 0 ) { //�ΏۂɃv���|�[�Y���Ă��邩�𒲂ׂ�A�v���|�[�Y���Ă��Ȃ��ꍇ
						if ( WomanS[ManQ[i][PartnerJ]] == Integer.MAX_VALUE ) { //�Ώۂ�����
							WomanS[ManQ[i][PartnerJ]] = i; //i�̐l����Ԍ����������������Ɛg�Ȃ��i�̐l�ƌ�������
							ManS[i] = ManQ[i][PartnerJ]; //��]�����ƌ����Ə�������
							proposeman[i][ ManQ[i][PartnerJ] ] = 2;//�v���|�[�Y�\���X�V���� 2���ƌ���
						}
						else if ( WomanS[ ManQ[i][PartnerJ] ] < Integer.MAX_VALUE ) { //�Ώۂ̏��������Ɍ������Ă�����
								if ( WomanQRev[ ManQ[i][PartnerJ] ][i] > WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //�t�I�l�\�̑Ώۏ����ɂƂ���i�̐l���ォ�A����Ƃ����̌�������̂��ォ�ǂ���
									proposeman[i][ ManQ[i][PartnerJ] ] = 1; //���łɌ������Ă�l�̂ق����D���ȂƂ��@�͌���ꂽ�̂Ńv���|�[�Y����
								}
								else if ( WomanQRev[ ManQ[i][PartnerJ] ][i] < WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //�V�����l�̂��D��
									proposeman[ WomanS[ ManQ[i][PartnerJ] ] ][ ManQ[i][PartnerJ] ] = 1; //����j��
									ManS [ WomanS[ ManQ[i][PartnerJ] ] ] = Integer.MAX_VALUE; //��l�Ƃ��������񖢍���
									WomanS[ ManQ[i][0] ] = Integer.MAX_VALUE;
									proposeman[i][ ManQ[i][PartnerJ] ] = 2; //�V��������
									ManS[i] = ManQ[i][PartnerJ]; //���݂��̌��������ύX
									WomanS[ManQ[i][PartnerJ]] = i;
								}					
						}
					}
					else if ( proposeman[i][ ManQ[i][PartnerJ] ] > 0 ) { //�Ώۂ��v���|�[�Y���Ă����茋�����Ă����ꍇ
						PartnerJ += 1;
						if ( WomanS[ManQ[i][PartnerJ]] == Integer.MAX_VALUE ) { //�Ώۂ�����
							WomanS[ManQ[i][PartnerJ]] = i; //i�̐l����Ԍ����������������Ɛg�Ȃ��i�̐l�ƌ�������
							ManS[i] = ManQ[i][PartnerJ]; //��]�����ƌ����Ə�������
							proposeman[i][ ManQ[i][PartnerJ] ] = 2;//�v���|�[�Y�\���X�V���� 2���ƌ���
						}
						else if ( WomanS[ ManQ[i][PartnerJ] ] < Integer.MAX_VALUE ) { //�Ώۂ̏��������Ɍ������Ă�����
								if ( WomanQRev[ ManQ[i][PartnerJ] ][i] > WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //�t�I�l�\�̑Ώۏ����ɂƂ���i�̐l���ォ�A����Ƃ����̌�������̂��ォ�ǂ���
									proposeman[i][ ManQ[i][PartnerJ] ] = 1; //���łɌ������Ă�l�̂ق����D���ȂƂ��@�͌���ꂽ�̂Ńv���|�[�Y����
								}
								else if ( WomanQRev[ ManQ[i][PartnerJ] ][i] < WomanQRev[ ManQ[i][PartnerJ] ][ WomanS[ ManQ[i][PartnerJ] ] ] ) { //�V�����l�̂��D��
									proposeman[ WomanS[ ManQ[i][PartnerJ] ] ][ ManQ[i][PartnerJ] ] = 1; //����j��
									ManS [ WomanS[ ManQ[i][PartnerJ] ] ] = Integer.MAX_VALUE; //��l�Ƃ��������񖢍���
									WomanS[ ManQ[i][0] ] = Integer.MAX_VALUE;
									proposeman[i][ ManQ[i][PartnerJ] ] = 2; //�V��������
									ManS[i] = ManQ[i][PartnerJ]; //���݂��̌��������ύX
									WomanS[ManQ[i][PartnerJ]] = i;
								}					
						}
					}
				}
			}
			for ( int i = 0; i < ManS.length; i++ ) { //�t���O����
				if ( ManS[i] == Integer.MAX_VALUE ) flug = 1;
			}
		} while ( flug == 1 ); //������̐l������
		
		LastView ( ManS );
		QView ( proposeman );
	}
}
