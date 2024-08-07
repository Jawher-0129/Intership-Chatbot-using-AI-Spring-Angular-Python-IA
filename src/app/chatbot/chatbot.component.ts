import { Component, OnInit } from '@angular/core';
import { ServiceUserService } from '../service-user.service';

@Component({
  selector: 'app-chatbot',
  templateUrl: './chatbot.component.html',
  styleUrls: ['./chatbot.component.css']
})
export class ChatbotComponent implements OnInit {
  message: string = '';
  response: string = '';
  chatHistory: { sender: string, message: string }[] = [];

  constructor(private service: ServiceUserService) {}

  sendMessage(): void {
    if (this.message.trim() !== '') {
      this.chatHistory.push({ sender: 'user', message: this.message });
      this.service.getChatbotResponse(this.message).subscribe(res => {
        this.chatHistory.push({ sender: 'bot', message: res.response });
      });
      this.message = '';
    }
  }

  ngOnInit() {}
}
