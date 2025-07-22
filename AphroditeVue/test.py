#include<bits/stdc++.h>
using namespace std;
int a[10000000]={1};
int b[1000005]={1};
int kt=0,js=1,sum=1;
void cr(int x,int y){
	a[js]=y;
	for(int i=sum;i>x;i--){
		b[i]=b[i-1];
	}
	b[x]=js;
	b[x+1]=b[x+2];
	sum++;
	js++;
}
void sc(int x){
	a[x-1]=a[x];
	for(int i=x+1;i<sum;i++){
		a[i-1]=a[i];
	}
	sum--;
}
int cx(int x){
	if(x>=sum){
		return 0;
	}
	return a[b[x-1]];
}
int main(){
	int q;
	cin>>q;
	for(int i=0;i<q;i++){
		int q1;
		cin>>q1;
		if(q1==1){
			int q2,q3;
			cin>>q2>>q3;
			cr(q2,q3);
		}else if(q1==2){
			int q2;
			cin>>q2;
			int qa=cx(q2);
			cout<<qa<<endl;
		}else if(q1==3){
			int q2;
			cin>>q2;
			sc(q2);
		}
	}
    return 0;
}