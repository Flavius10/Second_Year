#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

void reverse_string(char* str){
        int n = strlen(str);

        for(int i = 0; i < n / 2; ++i)
        {
                char temp = str[i];
                str[i] = str[n - i - 1];
                str[n - i - 1] = temp;
        }

}

int main(){
        int sockfd, clientfd;
        struct sockaddr_in server_addr, client_addr;
        socklen_t client_len;
        char buffer[1024];

        sockfd = socket(AF_INET, SOCK_STREAM, 0);
        server_addr.sin_family = AF_INET;
        server_addr.sin_port = htons(8080);
        server_addr.sin_addr.s_addr = INADDR_ANY;

        bind(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr));

        listen(sockfd, 1);
        printf("Server pornit. Asteptam conexiune...\n");

        client_len = sizeof(client_addr);
        clientfd = accept(sockfd, (struct sockaddr*)&client_addr, &client_len);

        read(clientfd, buffer, sizeof(buffer));
        printf("Primit: %s\n", buffer);

        reverse_string(buffer);
        write(clientfd, buffer, strlen(buffer) + 1);

        close(clientfd);
        close(sockfd);

        return 0;
}