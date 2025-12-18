namespace ComputerUpgrade.Classes
{
    public class Component(string name, double price, string type, string store, string image)
    {
        public string? Name { get; } = name;
        public double Price { get; } = price;
        public string? Type { get; } = type;
        public string? Store { get; } = store;
        public string? Image { get; } = image;

        private const string BundleKey = "CPU / Motherboard / RAM Bundle";
        private const string GPUKey = "Asus TUF Gaming OC GeForce RTX 5070 Ti 16GB";
        private const string StorageKey = "Samsung 990 Pro 2TB M.2 NVME";
        private const string PowerSupplyKey = "Corsair RM850x 850 Watt 80 Plus Gold Fully Modular ATX Power Supply";
        //private const string KeyboardKey = "K70 RGB PRO Mechanical Gaming Keyboard";

        //private const string MouseKey = "M75 Lightweight RGB Gaming Mouse";
        //private const string HeadsetKey = "Virtuoso RGB Wireless XT";
        private const string CoolerKey = "Corsair iCUE Link Titan 360 RX RGB AIO";
        private const string CaseKey = "Corsair Frame 5000D RS ARGB";

        public static readonly Dictionary<string, string> MicrocenterUrlMap = new()
        {
            { BundleKey, "https://www.microcenter.com/site/content/custom-pc-builder-amd.aspx?k=1765440127411" },
            { GPUKey, "https://www.microcenter.com/product/690046/asus-nvidia-geforce-rtx-5070-ti-tuf-gaming-overclocked-triple-fan-16gb-gddr7-pcie-50-graphics-card" },
            { StorageKey, "https://www.microcenter.com/product/660429/samsung-990-pro-2tb-samsung-v-nand-3-bit-mlc-pcie-gen-4-x4-nvme-m2-internal-ssd" },
            { PowerSupplyKey, "https://www.microcenter.com/product/662713/corsair-rmx-shift-series-rm850x-850-watt-80-plus-gold-fully-modular-atx-power-supply" },
            //{ KeyboardKey, "https://www.microcenter.com/product/645897/corsair-k70-rgb-champion-series-wired-mechanical-gaming-keyboard" }
        };

        public static readonly Dictionary<string, string> CorsairUrlMap = new()
        {
            { CoolerKey, "https://www.corsair.com/us/en/p/cpu-coolers/cw-9061018-ww/icue-link-titan-360-rx-rgb-aio-liquid-cpu-cooler-cw-9061018-ww" },
            { CaseKey, "https://www.corsair.com/us/en/p/pc-cases/cc-9011309-ww/frame-5000d-rs-argb-high-airflow-mid-tower-pc-case-cc-9011309-ww" },
            //{ MouseKey, "https://www.corsair.com/us/en/p/gaming-mouse/ch-930d010-na/m75-lightweight-rgb-gaming-mouse-ch-930d010-na" },
            //{ HeadsetKey, "https://www.corsair.com/us/en/p/gaming-headsets/ca-9011188-na/virtuoso-rgb-wireless-xt-high-fidelity-gaming-headset-slate-ca-9011188-na" }
        };

        public static readonly Dictionary<string, string> AllUrlMap = new()
        {
            { BundleKey, "https://www.microcenter.com/site/content/custom-pc-builder-amd.aspx?k=1765921300386" },
            { CoolerKey, "https://www.corsair.com/us/en/p/cpu-coolers/cw-9061018-ww/icue-link-titan-360-rx-rgb-aio-liquid-cpu-cooler-cw-9061018-ww" },
            { GPUKey, "https://www.microcenter.com/product/690046/asus-nvidia-geforce-rtx-5070-ti-tuf-gaming-overclocked-triple-fan-16gb-gddr7-pcie-50-graphics-card" },
            { StorageKey, "https://www.microcenter.com/product/660429/samsung-990-pro-2tb-samsung-v-nand-3-bit-mlc-pcie-gen-4-x4-nvme-m2-internal-ssd" },
            { PowerSupplyKey, "https://www.microcenter.com/product/662713/corsair-rmx-shift-series-rm850x-850-watt-80-plus-gold-fully-modular-atx-power-supply" },
            { CaseKey, "https://www.corsair.com/us/en/p/pc-cases/cc-9011309-ww/frame-5000d-rs-argb-high-airflow-mid-tower-pc-case-cc-9011309-ww" },
            //{ KeyboardKey, "https://www.microcenter.com/product/645897/corsair-k70-rgb-champion-series-wired-mechanical-gaming-keyboard" },
            //{ MouseKey, "https://www.corsair.com/us/en/p/gaming-mouse/ch-930d010-na/m75-lightweight-rgb-gaming-mouse-ch-930d010-na" },
            //{ HeadsetKey, "https://www.corsair.com/us/en/p/gaming-headsets/ca-9011188-na/virtuoso-rgb-wireless-xt-high-fidelity-gaming-headset-slate-ca-9011188-na" }
        };

        public static readonly List<Component> MicrocenterComponents =
        [
            new(BundleKey, 679.99, "Bundle Components (Motherboard, CPU, RAM)", "Microcenter", "~/images/bundle.png"),
            new(GPUKey, 849.99, "Graphics Card", "Microcenter", "~/images/gpu.png"),
            new(StorageKey, 209.99, "Storage SSD", "Microcenter", "~/images/ssd.png"),
            new(PowerSupplyKey, 114.99, "Power Supply", "Microcenter", "~/images/powerSupply.png"),
            //new(KeyboardKey, 94.99, "Keyboard", "~/images/keyboard.png"),
        ];

        public static readonly List<Component> CorsairComponents =
        [
            new(CoolerKey, 199.99, "CPU Cooler", "Corsair Online", "~/images/cooler.png"),
            new(CaseKey, 199.99, "Case", "Corsair Online", "~/images/case.png"),
            //new(MouseKey, 59.99, "Mouse", "~/images/mouse.png"),
            //new(HeadsetKey, 279.99, "Headset", "~/images/headset.png")
        ];

        public static readonly List<Component> AllComponents = MicrocenterComponents.Union(CorsairComponents).ToList();

        public static double CalculateTotalPrice(string retailer)
        {
            return retailer switch
            {
                "Microcenter" => MicrocenterComponents.Sum(component => component.Price),
                "Corsair" => CorsairComponents.Sum(component => component.Price),
                _ => MicrocenterComponents.Sum(component => component.Price) + CorsairComponents.Sum(component => component.Price)
            };
        }
    }
}