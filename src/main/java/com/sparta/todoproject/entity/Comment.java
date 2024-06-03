package com.sparta.todoproject.entity;

import com.sparta.todoproject.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor // 이거 필요한 이유??
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    // fetch 타입..?
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentRequestDto requestDto, Schedule schedule, User user) {
        this.contents = requestDto.getContents();
        this.schedule = schedule;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
