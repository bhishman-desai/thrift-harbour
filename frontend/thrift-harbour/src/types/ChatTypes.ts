export interface UserChatType {
  userID: number;
  firstName: string;
  lastName: string;
  email: string;
  notification: boolean;
}

export interface ChatMessageType {
  id?: number;
  chatId?: string;
  senderId: string;
  recipientId: string;
  content: string;
  timestamp?: string | null;
}
