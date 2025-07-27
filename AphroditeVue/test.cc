#include <bits/stdc++.h>
using namespace std;

bool isPrime(int num) {
  // ....
}

int main() {
  for (int i = 6; i <= 100; i += 2) {

    for (int first = 2; first <= i / 2; first++) {

      if (isPrime(first) && isPrime(i - first)) {
        cout << i << "=" << first << "+" << (i - first) << endl;
        break;
      }
    }
  }

  return 0;
}
