namespace ComputerUpgrade.Classes
{
    public class Component(string name, string type, double price, bool available)
    {
        public string? Name { get; } = name;
        public string? Type { get; } = type;
        public double Price { get; } = price;
        public bool Available { get; } = available;

        private const string BundleKey = "CPU / Motherboard / RAM Bundle";
        private const string GPUKey = "Asus TUF Gaming OC GeForce RTX 5070 Ti 16GB";
        private const string CoolerKey = "Corsair iCUE Link Titan 360 RX RGB AIO";
        private const string PowerSupplyKey = "Corsair RM850x 850 Watt 80 Plus Gold Fully Modular ATX Power Supply";
        private const string StorageKey = "Samsung 990 Pro 2TB M.2 NVME";
        private const string CaseKey = "Corsair Frame 5000D RS ARGB";
        private const string FanThreePackKey = "Corsair iCUE LINK QX 120 RGB - 3 Pack";
        private const string FanOnePackKey = "Corsair iCUE LINK RX 120 RGB - 1 Pack";

        public static readonly Dictionary<string, string> MicrocenterUrlMap = new()
        {
            { BundleKey, "https://www.microcenter.com/product/5007091/amd-ryzen-7-7800x3d,-asus-b650e-e-tuf-gaming,-gskill-flare-x5-series-32gb-ddr5-6000-kit,-computer-build-bundle" },
            { GPUKey, "https://www.microcenter.com/product/690046/asus-nvidia-geforce-rtx-5070-ti-tuf-gaming-overclocked-triple-fan-16gb-gddr7-pcie-50-graphics-card" },
            { CoolerKey, "https://www.microcenter.com/product/683539/corsair-icue-link-titan-360-rx-rgb-360mm-water-cooling-kit-black" },
            { PowerSupplyKey, "https://www.microcenter.com/product/686841/corsair-rm850x-850-watt-cybenetics-gold-atx-fully-modular-power-supply-atx-31-compatible" },
            { StorageKey, "https://www.microcenter.com/product/660429/samsung-990-pro-2tb-samsung-v-nand-3-bit-mlc-pcie-gen-4-x4-nvme-m2-internal-ssd" },
            { CaseKey, "https://www.microcenter.com/product/688606/corsair-frame-4000d-rs-argb-modular-tempered-glass-atx-mid-tower-computer-case-black" },
            { FanThreePackKey, "https://www.microcenter.com/product/688606/corsair-frame-4000d-rs-argb-modular-tempered-glass-atx-mid-tower-computer-case-black" },
            { FanOnePackKey, "https://www.microcenter.com/product/688606/corsair-frame-4000d-rs-argb-modular-tempered-glass-atx-mid-tower-computer-case-black" }
        };

        public static readonly List<Component> MicrocenterComponents =
        [
            new(BundleKey, "Bundle Components (Motherboard, CPU, RAM)", 579.99, false),
            new(GPUKey, "Graphics Card", 849.99, false),
            new(CoolerKey, "CPU Cooler", 199.99, false),
            new(PowerSupplyKey, "Power Supply", 169.99, false),
            new(StorageKey, "SSD Storage", 229.99, false),
            new(CaseKey, "Case", 124.99, false),
            new(FanThreePackKey, "Fan 3 Pack", 117.99, false),
            new(FanOnePackKey, "Fan 1 Pack", 34.99, false)
        ];

        public static double CalculateTotalPrice()
        {
            return MicrocenterComponents.Sum(component => component.Price);
        }
    }
}