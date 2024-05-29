package com.sparta.todoproject.entity;

import com.sparta.todoproject.dto.CommentAccessRequestDto;
import com.sparta.todoproject.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor // 이거 필요한 이유??
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = "user_id", nullable = false)
    private String userId;

    // fetch 타입..?
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(CommentRequestDto requestDto, Schedule schedule) {
        this.contents = requestDto.getContents();
        this.userId = requestDto.getUserId();
        this.schedule = schedule;
    }

    public void update(CommentAccessRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
