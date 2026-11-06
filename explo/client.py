import socket
import threading
import sys

# Fonction qui tourne en boucle pour afficher tout ce que Java envoie
def recevoir_messages(sock):
    while True:
        try:
            # On lit jusqu'à 4096 octets et on décode le texte
            message = sock.recv(4096).decode('utf-8')
            if message:
                flush = True
                print(message, end='')
            else:
                print("\n[🔌 Le serveur Java a coupé la connexion]")
                sock.close()
                sys.exit(0)
        except:
            print("\n[❌ Erreur de connexion avec le serveur]")
            break

def main():
    host = '127.0.0.1' # Se connecte en local (localhost)
    port = 5555

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    
    try:
        client_socket.connect((host, port))
        print(f"✅ Connecté avec succès au serveur Java ({host}:{port})\n")
    except Exception as e:
        print(f"❌ Impossible de trouver le serveur Java: {e}")
        return

    # On lance l'écoute des messages en tâche de fond (Thread)
    thread_ecoute = threading.Thread(target=recevoir_messages, args=(client_socket,))
    thread_ecoute.daemon = True
    thread_ecoute.start()

    # Boucle principale : tout ce qu'on tape est envoyé à Java
    while True:
        try:
            commande = input()
            # On envoie le texte + un retour à la ligne pour que Java comprenne la fin
            client_socket.sendall((commande + '\n').encode('utf-8'))
            if commande.lower() == 'quitter':
                break
        except KeyboardInterrupt:
            break

    client_socket.close()

if __name__ == "__main__":
    main()