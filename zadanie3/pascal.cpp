#include <iostream>

int main(int argc, char* argv[]) {
    try {
        int n = std::stoi(argv[1]);
        int pascal[n];
        pascal[0] = 1;
        for(int i = 1; i < n; ++i) {
            pascal[i] = 0;
        };
        while(pascal[n-1] == 0) {
            for (int i = n - 1; i > 0; --i) {
                pascal[i] = pascal[i - 1] + pascal[i];
            }
        }
        for (int i = 0; i < n; ++i) {
            std::cout << pascal[i] << " ";
        }
        std::cout << std::endl;
    }
    catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}