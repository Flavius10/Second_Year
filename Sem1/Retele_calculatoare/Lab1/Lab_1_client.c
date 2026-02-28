#include <string.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>

int main(){

        int sockfd;
        struct sockaddr_in server_addr;
        char buffer[1024];

        sockfd = socket(AF_INET, SOCK_STREAM, 0);
        server_addr.sin_family = AF_INET;
        server_addr.sin_port = htons(8080);
        server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

        connect(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr));

        printf("Introdu un sir: ");
        fgets(buffer, sizeof(buffer), stdin);
        buffer[strcmp(buffer, "\n")] = '\0';

        write(sockfd, buffer, strlen(buffer) + 1);
        read(sockfd, buffer, sizeof(buffer));

        printf("Raspuns de la server: %s\n", buffer);

        close(sockfd);

        return 0;

}