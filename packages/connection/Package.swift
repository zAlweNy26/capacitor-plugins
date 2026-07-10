// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "Connection",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "Connection",
            targets: ["ConnectionPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "ConnectionPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/ConnectionPlugin"),
        .testTarget(
            name: "ConnectionPluginTests",
            dependencies: ["ConnectionPlugin"],
            path: "ios/Tests/ConnectionPluginTests")
    ]
)