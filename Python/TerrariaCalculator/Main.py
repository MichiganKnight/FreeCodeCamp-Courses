def main():
    print("=== Terraria Cost Calculator ===")

    while True:
        item = input("Enter Item Name: ")

        if item == "quit":
            break

        cost_gold = int(input("Enter Item Cost (Gold): "))
        cost_silver = int(input("Enter Item Cost (Silver): "))
        cost_copper = int(input("Enter Item Cost (Copper): "))
        desired_quantity = int(input("Enter Desired Quantity: "))

        total_gold, total_silver, total_copper = calculate_cost(desired_quantity, cost_gold, cost_silver, cost_copper)

        print()
        print(
            f"Total Cost: {desired_quantity} {item}(s): {total_gold} Gold | {total_silver} Silver | {total_copper} Copper")

def calculate_cost(quantity, gold, silver, copper):
    cost_per_item_copper = (gold * 10000) + (silver * 100) + copper
    total_cost_copper = cost_per_item_copper * quantity

    total_gold = total_cost_copper // 10000
    remaining_copper_after_gold = total_cost_copper % 10000

    total_silver = remaining_copper_after_gold // 100
    total_copper = remaining_copper_after_gold % 100

    return total_gold, total_silver, total_copper

if __name__ == '__main__':
    main()