package com.example.springdatajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "age")
  private Integer age;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public Member(String username) {
    this.username = username;
  }

  public Member(String username, Integer age, Team team) {
    this.username = username;
    this.age = age;

    if (team != null) {
      changeTeam(team);
    }

  }

  public Member(String username, Integer age) {
    this.username = username;
    this.age = age;
  }

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }
}

