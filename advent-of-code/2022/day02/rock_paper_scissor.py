from file_utilities import read_lines


class RockPaperScissorGame:
    choice = {
        'A': 'rock',
        'X': 'rock',
        'B': 'paper',
        'Y': 'paper',
        'C': 'scissor',
        'Z': 'scissor',
    }
    rules_1 = {
        "rock": {
            "scissor": "rock",
            "paper": "paper",
            "rock": "draw"
        },
        "paper": {
            "scissor": "scissor",
            "rock": "paper",
            "paper": "draw",
        },
        "scissor": {
            "paper": "scissor",
            "rock": "rock",
            "scissor": "draw",
        }
    }

    points = {
        'rock': 1,
        'paper': 2,
        'scissor': 3
    }

    rules_2 = {
        "rock": {
            'X': "scissor",
            'Y': "rock",
            'Z': "paper"
        },
        "paper": {
            'X': "rock",
            'Y': "paper",
            'Z': "scissor"
        },
        "scissor": {
            'X': "paper",
            'Y': "scissor",
            'Z': "rock"
        }
    }
    win_point = 6
    lose_point = 0
    draw_point = 3
    score = 0
    score_2 = 0

    point_system_2 = {
        'X': 0,
        'Y': 3,
        'Z': 6
    }

    def calculate_points(self, player2, winner):
        self.score += self.points.get(player2)
        if winner in ['X', 'Y', 'Z']:
            self.score += self.win_point

        if winner in ['draw']:
            self.score += self.draw_point

    def play_game(self, player1, player2):
        type1 = self.choice.get(player1)
        type2 = self.choice.get(player2)
        winner = self.rules_1[type1][type2]
        winner_player = ''
        if type1 == winner:
            winner_player = player1

        if type2 == winner:
            winner_player = player2

        if winner == 'draw':
            winner_player = 'draw'

        self.calculate_points(type2, winner_player)

        return self.score

    def part_1(self, input):
        for item in input:
            opponent1, opponent2 = item.split(" ")
            self.play_game(opponent1, opponent2)

        return self.score

    def play_game_2(self, player1, outcome):
        type1 = self.choice.get(player1)
        option_played = self.rules_2[type1][outcome]

        if outcome in ['Y', 'Z']:
            self.score_2 += self.point_system_2.get(outcome) + self.points.get(option_played)
        else:
            self.score_2 += self.points.get(option_played)


    def part_2(self, input):
        for item in input:
            opponent1, opponent2 = item.split(" ")
            self.play_game_2(opponent1, opponent2)

        return self.score_2


if __name__ == '__main__':
    data = read_lines("./data/input.txt")
    print(f"part 1 {RockPaperScissorGame().part_1(data)}") # part 1 13682
    print(f"part 2 {RockPaperScissorGame().part_2(data)}") # part 2 12881